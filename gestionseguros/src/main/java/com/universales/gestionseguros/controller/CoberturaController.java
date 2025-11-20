package com.universales.gestionseguros.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.universales.gestionseguros.dto.CoberturaDto;
import com.universales.gestionseguros.dto.OkResponseDto;
import com.universales.entity.Cobertura;
import com.universales.gestionseguros.repository.CoberturaRepository;
import com.universales.gestionseguros.service.PolizaCoberturaService;

import lombok.Data;

@Data
@RestController
@RequestMapping("/api/auth/coberturas")
public class CoberturaController {

	private final PolizaCoberturaService polizaCoberturaService;
	private final CoberturaRepository coberturaRepository;

	private static final Log LOG = LogFactory.getLog(CoberturaController.class);

	public CoberturaController(CoberturaRepository coberturaRepository, PolizaCoberturaService polizaCoberturaService) {
		this.coberturaRepository = coberturaRepository;
		this.polizaCoberturaService = polizaCoberturaService;
	}

	@GetMapping("/obtener-coberturas")
	public ResponseEntity<List<Cobertura>> obtenerCoberturas() {
		try {
			return ResponseEntity.ok(coberturaRepository.findAll());
		} catch (Exception e) {
			LOG.error("Error al buscar coberturas", e);
			return ResponseEntity.status(500).build();
		}
	}

	@GetMapping("/obtener-coberturas-poliza")
	public ResponseEntity<List<Map<String, Object>>> buscarCoberturasPoliza(@RequestParam Integer idPoliza) {
		try {
		return ResponseEntity.ok(polizaCoberturaService.buscarCoberturasPoliza(idPoliza));
		}catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

	@PostMapping("/crear-cobertura")
	public ResponseEntity<OkResponseDto> crearCobertura(@Validated @RequestBody CoberturaDto coberturaDto) {
		OkResponseDto response = new OkResponseDto();

		try {
			if (coberturaDto.getIdCobertura() != null) {
				Cobertura cobertura = coberturaRepository.findById(coberturaDto.getIdCobertura())
						.orElseThrow(() -> new RuntimeException("Cobertura no encontrada"));

				cobertura.setNombre(coberturaDto.getNombre());
				cobertura.setSumaAsegurada(coberturaDto.getSumaAsegurada());
				cobertura.setPrimaNeta(coberturaDto.getPrimaNeta());
				cobertura.setComisionVenta(coberturaDto.getComisionVenta());
				cobertura.setGastoEmision(coberturaDto.getGastoEmision());
				cobertura.setPrimaTotalConIva(coberturaDto.getPrimaTotalConIva());
				cobertura.setPrimaTotalSinIva(coberturaDto.getPrimaTotalSinIva());
				cobertura.setEstado(coberturaDto.getEstado());

				Cobertura updatedCobertura = coberturaRepository.save(cobertura);
				response.setResponse(
						String.format("Cobertura con ID %d actualizada con éxito", updatedCobertura.getIdCobertura()));

				return new ResponseEntity<>(response, HttpStatus.OK); 
			}

			Cobertura newCobertura = new Cobertura();
			newCobertura.setNombre(coberturaDto.getNombre());
			newCobertura.setSumaAsegurada(coberturaDto.getSumaAsegurada());
			newCobertura.setPrimaNeta(coberturaDto.getPrimaNeta());
			newCobertura.setComisionVenta(coberturaDto.getComisionVenta());
			newCobertura.setGastoEmision(coberturaDto.getGastoEmision());
			newCobertura.setPrimaTotalConIva(coberturaDto.getPrimaTotalConIva());
			newCobertura.setPrimaTotalSinIva(coberturaDto.getPrimaTotalSinIva());
			newCobertura.setEstado(coberturaDto.getEstado() != null ? coberturaDto.getEstado() : "Activa");

			Cobertura savedCobertura = coberturaRepository.save(newCobertura);
			response.setResponse(
					String.format("Cobertura con ID %d creada con éxito", savedCobertura.getIdCobertura()));
			return new ResponseEntity<>(response, HttpStatus.CREATED);

		} catch (RuntimeException e) {
			
			response.setResponse("Error: " + e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			
			response.setResponse("Error interno del servidor al procesar la cobertura.");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}