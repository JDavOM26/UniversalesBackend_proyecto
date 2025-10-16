package com.universales.gestionseguros.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.universales.gestionseguros.dto.PolizaDto;
import com.universales.gestionseguros.repository.PolizaRepository;
import com.universales.gestionseguros.service.BuscarPolizaService;
import com.universales.gestionseguros.service.EmitirPolizaService;

import java.util.Collections;
import java.util.List;
import java.util.Map;


import org.modelmapper.ModelMapper;

@RestController
@RequestMapping("/api/auth/polizas")
public class PolizaController {

	private final EmitirPolizaService polizaService;
	private final BuscarPolizaService buscarPolizaService;

	public PolizaController(BuscarPolizaService buscarPolizaService, EmitirPolizaService polizaService, PolizaRepository polizaRepository, ModelMapper modelMapper) {
		this.polizaService = polizaService;
		this.buscarPolizaService = buscarPolizaService;
	}
	
	
	 @GetMapping("/buscar-poliza")
	    public ResponseEntity<List<Map<String, Object>>> encontrarClientePorDocumento(
	            @RequestParam String valor) {
	        try {
	            if (valor != null && !valor.trim().isEmpty()) {
	                List<Map<String, Object>> resultados = buscarPolizaService.buscarCoberturasPoliza(valor);
	                return ResponseEntity.ok(resultados);
	            } else {
	                throw new IllegalArgumentException("El valor de búsqueda no puede ser nulo o vacío");
	            }
	        } catch (IllegalArgumentException e) {
	            return ResponseEntity.badRequest().body(Collections.emptyList());
	        } catch (RuntimeException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
	        }
	    }

	@PostMapping("/emitir-poliza")
    public ResponseEntity<String> emitirPoliza(@RequestBody PolizaDto polizaDto) {
        try {
            polizaService.emitirPoliza(polizaDto);
            
            return new ResponseEntity<>( "Póliza creada exitosamente",HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Error al crear la póliza: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error inesperado al crear la póliza: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}