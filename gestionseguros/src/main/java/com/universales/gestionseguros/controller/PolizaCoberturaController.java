package com.universales.gestionseguros.controller;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.universales.gestionseguros.service.PolizaCoberturaService;

import lombok.Data;

@Data
@RestController
@RequestMapping("/api/auth/poliza-cobertura")
public class PolizaCoberturaController {
	
	private final PolizaCoberturaService polizaCoberturaService;
	
	public PolizaCoberturaController( PolizaCoberturaService polizaCoberturaService) {
		this.polizaCoberturaService = polizaCoberturaService;
	}
	
	@GetMapping("/obtener-suma-asegurada")
    public ResponseEntity<BigDecimal> getSumaAseguradaDisponible(@RequestParam Integer idPoliza, Integer idCobertura) {
		try {
        BigDecimal sumaAsegurada = polizaCoberturaService.getSumaAseguradaDisponible(idPoliza, idCobertura);
        return ResponseEntity.ok(sumaAsegurada);
		}catch(Exception e) {
			return ResponseEntity.status(500).build();
		}
    }

}
