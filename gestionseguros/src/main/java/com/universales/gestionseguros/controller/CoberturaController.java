package com.universales.gestionseguros.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.universales.gestionseguros.dto.CoberturaDto;
import com.universales.gestionseguros.entity.Cobertura;
import com.universales.gestionseguros.repository.CoberturaRepository;
import com.universales.gestionseguros.service.PolizaCoberturaService;

import lombok.Data;

@Data
@RestController
@RequestMapping("/api/auth/coberturas")
public class CoberturaController {

	private static final BigDecimal IVA_PORCENTAJE = new BigDecimal("0.12");
	private final PolizaCoberturaService polizaCoberturaService;


	private final CoberturaRepository coberturaRepository;

	public CoberturaController(CoberturaRepository coberturaRepository, PolizaCoberturaService polizaCoberturaService) {
		this.coberturaRepository = coberturaRepository;
		this.polizaCoberturaService = polizaCoberturaService;
	}
	
	@GetMapping("/obtener-coberturas")
	public List<Cobertura> obtenerCoberturas() {
		return coberturaRepository.findAll();
	}
	
	@GetMapping("/obtener-coberturas-poliza")
	public List<Map<String, Object>> buscarCoberturasPoliza(@RequestParam Integer idPoliza){
		return polizaCoberturaService.buscarCoberturasPoliza(idPoliza);
	}

	@PostMapping("/crear-cobertura")
	public Cobertura crearCobertura(@Validated @RequestBody CoberturaDto coberturaDto) {
		
		BigDecimal primaTotalSinIva = calcularPrimaTotalSinIva(coberturaDto.getPrimaNeta(),
				coberturaDto.getGastoEmision(), coberturaDto.getComisionVenta());

		BigDecimal primaTotalConIva = calcularPrimaTotalConIva(primaTotalSinIva);
		
		Cobertura newCobertura = new Cobertura();
		newCobertura.setNombre(coberturaDto.getNombre());
		newCobertura.setSumaAsegurada(coberturaDto.getSumaAsegurada());
		newCobertura.setPrimaNeta(coberturaDto.getPrimaNeta());
		newCobertura.setComisionVenta(coberturaDto.getComisionVenta());
		newCobertura.setGastoEmision(coberturaDto.getGastoEmision());
		newCobertura.setPrimaTotalConIva(primaTotalConIva);
		newCobertura.setPrimaTotalSinIva(primaTotalSinIva);
		return coberturaRepository.save(newCobertura);
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
