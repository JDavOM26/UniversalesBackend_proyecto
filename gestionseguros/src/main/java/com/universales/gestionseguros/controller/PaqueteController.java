package com.universales.gestionseguros.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.universales.gestionseguros.dto.PaqueteDto;
import com.universales.gestionseguros.entity.Cobertura;
import com.universales.gestionseguros.entity.Paquete;
import com.universales.gestionseguros.repository.CoberturaRepository;
import com.universales.gestionseguros.repository.PaqueteRepository;
import com.universales.gestionseguros.service.PaqueteCoberturaService;

import lombok.Data;

@Data
@RestController
@RequestMapping("/api/auth/paquetes")
public class PaqueteController {

	private static final BigDecimal IVA_PORCENTAJE = new BigDecimal("0.12");

	private final PaqueteRepository paqueteRepository;
	private final CoberturaRepository coberturaRepository;
	private final PaqueteCoberturaService paqueteCoberturaService;

	public PaqueteController(PaqueteRepository paqueteRepository, PaqueteCoberturaService paqueteCoberturaService,
			CoberturaRepository coberturaRepository) {
		this.paqueteRepository = paqueteRepository;
		this.paqueteCoberturaService = paqueteCoberturaService;
		this.coberturaRepository = coberturaRepository;
	}
	
	@GetMapping("/obtener-paquetes")
	public List<Paquete> obtenerPaquetes() {
		return paqueteRepository.findAll();
	}

	@PostMapping("/crear-paquete")
	public ResponseEntity<Paquete> crearPaquete(@Validated @RequestBody PaqueteDto paqueteDto) {
		try {
			BigDecimal[] valoresCobertura = calcularValoresCobertura(paqueteDto.getIdCobertura());
			BigDecimal primaNetaTotal = valoresCobertura[0];
			BigDecimal gastoEmisionTotal = valoresCobertura[1];
			BigDecimal comisionVentaTotal = valoresCobertura[2];

			BigDecimal primaTotalSinIva = calcularPrimaTotalSinIva(primaNetaTotal, gastoEmisionTotal,
					comisionVentaTotal);

			BigDecimal primaTotalConIva = calcularPrimaTotalConIva(primaTotalSinIva);

			Paquete newPackage = new Paquete();
			newPackage.setNombre(paqueteDto.getNombre());

			newPackage.setPrimaNeta(primaNetaTotal);
			newPackage.setComisionVenta(comisionVentaTotal);
			newPackage.setGastoEmision(gastoEmisionTotal);
			newPackage.setPrimaTotalSinIva(primaTotalSinIva);
			newPackage.setPrimaTotalConIva(primaTotalConIva);

			Paquete savedPackage = paqueteRepository.save(newPackage);

			paqueteCoberturaService.crearPaqueteCobertura(paqueteDto.getIdCobertura(), savedPackage.getIdPaquete());

			return new ResponseEntity<>(savedPackage, HttpStatus.CREATED);
		} catch (Exception e) {

			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Error al crear el usuario: " + e.getMessage());
		}
	}

	private BigDecimal[] calcularValoresCobertura(List<Integer> idCoberturas) {
		BigDecimal primaNetaTotal = BigDecimal.ZERO;
		BigDecimal gastoEmisionTotal = BigDecimal.ZERO;
		BigDecimal comisionVentaTotal = BigDecimal.ZERO;

		for (Integer idCobertura : idCoberturas) {
			Cobertura cobertura = coberturaRepository.findById(idCobertura)
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
							"Cobertura con ID " + idCobertura + " no encontrada"));
			if (cobertura != null) {
				primaNetaTotal = primaNetaTotal
						.add(cobertura.getPrimaNeta() != null ? cobertura.getPrimaNeta() : BigDecimal.ZERO);
				gastoEmisionTotal = gastoEmisionTotal
						.add(cobertura.getGastoEmision() != null ? cobertura.getGastoEmision() : BigDecimal.ZERO);
				comisionVentaTotal = comisionVentaTotal
						.add(cobertura.getComisionVenta() != null ? cobertura.getComisionVenta() : BigDecimal.ZERO);
			}
		}

		return new BigDecimal[] { primaNetaTotal,
				gastoEmisionTotal,
				comisionVentaTotal };
	}

	private BigDecimal calcularPrimaTotalSinIva(BigDecimal primaNeta, BigDecimal gastoEmision,
			BigDecimal comisionVenta) {
		  return primaNeta.add(gastoEmision).add(comisionVenta);
	}

	private BigDecimal calcularPrimaTotalConIva(BigDecimal primaTotalSinIva) {
		 BigDecimal iva = primaTotalSinIva.multiply(IVA_PORCENTAJE); 
		    return primaTotalSinIva.add(iva);
	}
}