package com.universales.gestionseguros.controller;
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
import com.universales.gestionseguros.entity.Paquete;
import com.universales.gestionseguros.repository.CoberturaRepository;
import com.universales.gestionseguros.repository.PaqueteRepository;
import com.universales.gestionseguros.service.PaqueteCoberturaService;

import lombok.Data;

@Data
@RestController
@RequestMapping("/api/auth/paquetes")
public class PaqueteController {



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
			

			

			Paquete newPackage = new Paquete();
			newPackage.setNombre(paqueteDto.getNombre());

			newPackage.setPrimaNeta(paqueteDto.getPrimaNeta());
			newPackage.setComisionVenta(paqueteDto.getComisionVenta());
			newPackage.setGastoEmision(paqueteDto.getGastoEmision());
			newPackage.setPrimaTotalSinIva(paqueteDto.getPrimaTotalSinIva());
			newPackage.setPrimaTotalConIva(paqueteDto.getPrimaTotalConIva());

			Paquete savedPackage = paqueteRepository.save(newPackage);

			paqueteCoberturaService.crearPaqueteCobertura(paqueteDto.getIdCobertura(), savedPackage.getIdPaquete());

			return new ResponseEntity<>(savedPackage, HttpStatus.CREATED);
		} catch (Exception e) {

			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Error al crear el usuario: " + e.getMessage());
		}
	}

	

	
}