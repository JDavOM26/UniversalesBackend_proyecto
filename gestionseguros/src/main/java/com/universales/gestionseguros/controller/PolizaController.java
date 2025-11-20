package com.universales.gestionseguros.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.universales.gestionseguros.dto.OkResponseDto;
import com.universales.gestionseguros.dto.PageResultPoliza;
import com.universales.gestionseguros.dto.PolizaDto;
import com.universales.entity.Poliza;
import com.universales.gestionseguros.repository.PolizaRepository;
import com.universales.gestionseguros.service.BuscarPolizaService;
import com.universales.gestionseguros.service.EmitirPolizaService;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@RestController
@RequestMapping("/api/auth/polizas")
public class PolizaController {

	private final PolizaRepository polizaRepository;
	private final EmitirPolizaService polizaService;
	private final BuscarPolizaService buscarPolizaService;

	public PolizaController(BuscarPolizaService buscarPolizaService, EmitirPolizaService polizaService,
			PolizaRepository polizaRepository) {
		this.polizaService = polizaService;
		this.buscarPolizaService = buscarPolizaService;
		this.polizaRepository = polizaRepository;

	}

	@GetMapping("/buscar-poliza-reclamo")
	public ResponseEntity<PageResultPoliza<Map<String, Object>>> encontrarClientePorDocumento(
			@RequestParam String valor, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
		try {
			if (valor == null || valor.trim().isEmpty()) {
				return ResponseEntity.badRequest().body(null);
			}

			PageResultPoliza<Map<String, Object>> resultado = buscarPolizaService.buscarCoberturasPoliza(valor.trim(),
					page, size);

			return ResponseEntity.ok(resultado);
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}

	}

	@GetMapping("/buscar-poliza-paginado")
	public ResponseEntity<Page<Poliza>> getAllPersonasPaginado(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
		try {
			return ResponseEntity.ok(polizaRepository.findAll(PageRequest.of(page, size)));
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}

	}

	@PostMapping("/emitir-poliza")
	public ResponseEntity<OkResponseDto> emitirPoliza(@RequestBody PolizaDto polizaDto) {
		OkResponseDto response = new OkResponseDto();
		try {

			response = polizaService.emitirPoliza(polizaDto);

			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			response.setResponse("Error al crear la póliza: " + e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			response.setResponse("Error inesperado al crear la póliza: " + e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}