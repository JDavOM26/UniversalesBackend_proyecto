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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.universales.gestionseguros.dto.EmitirReclamoDto;
import com.universales.gestionseguros.entity.PolizaCobertura;
import com.universales.gestionseguros.entity.Reclamo;
import com.universales.gestionseguros.repository.PolizaCoberturaRepository;
import com.universales.gestionseguros.repository.ReclamoRepository;

@RestController
@RequestMapping("/api/auth/reclamos")
public class ReclamoController {

	private final ReclamoRepository reclamoRepository;
	private final PolizaCoberturaRepository polizaCoberturaRepository;

	public ReclamoController(ReclamoRepository reclamoRepository, PolizaCoberturaRepository polizaCoberturaRepository) {
		this.reclamoRepository = reclamoRepository;
		this.polizaCoberturaRepository= polizaCoberturaRepository;
	}

	@PostMapping("/emitir-reclamo")
	public ResponseEntity<Reclamo> crearReclamo(@Validated @RequestBody EmitirReclamoDto reclamoDto) {
		try {
			Reclamo nuevoReclamo = new Reclamo();

			nuevoReclamo.setIdCobertura(reclamoDto.getIdCobertura());
			nuevoReclamo.setFechaSiniestro(reclamoDto.getFechaSiniestro());
			nuevoReclamo.setFechaIngresoReclamo(reclamoDto.getFechaIngresoReclamo());

			nuevoReclamo.setPrimerNombre(reclamoDto.getPrimerNombre());
			nuevoReclamo.setSegundoNombre(reclamoDto.getSegundoNombre());
			nuevoReclamo.setTercerNombre(reclamoDto.getTercerNombre());
			nuevoReclamo.setApellidoPrimero(reclamoDto.getApellidoPrimero());
			nuevoReclamo.setApellidoSegundo(reclamoDto.getApellidoSegundo());
			nuevoReclamo.setApellidoTercero(reclamoDto.getApellidoTercero());
			nuevoReclamo.setAjustador(reclamoDto.getAjustador());
			nuevoReclamo.setEstadoReclamo("Ingresado");
			nuevoReclamo.setIdPoliza(reclamoDto.getIdPoliza());
			
			Reclamo reclamoGuardado = reclamoRepository.save(nuevoReclamo);

			return new ResponseEntity<>(reclamoGuardado, HttpStatus.CREATED);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Error al crear el reclamo: " + e.getMessage());
		}
	}
	
	@GetMapping("/obtener-reclamos-usuario")
	public List<Reclamo> obtenerReclamoPorUsuario(@RequestParam Integer ajustador) {

		return reclamoRepository.findByAjustador(ajustador);
		
	}
	
	@GetMapping("/obtener-reclamos-ingresados")
	public List<Reclamo> obtenerReclamosIngresados() {
        String estadoReclamo = "Ingresado";
		return reclamoRepository.findByEstadoReclamo(estadoReclamo);
		
	}
	
	@PostMapping("/aprobar-reclamo")
	public ResponseEntity<Reclamo> aprobarReclamo(@Validated @RequestBody EmitirReclamoDto reclamoDto) {
		try {
			Reclamo aprobarReclamo = reclamoRepository.findById(reclamoDto.getIdReclamo())
					.orElseThrow(() -> new RuntimeException("Póliza no encontrada"));

			aprobarReclamo.setPerito(reclamoDto.getPerito());
			aprobarReclamo.setMontoAprobado(reclamoDto.getMontoAprobado());
			aprobarReclamo.setEstadoReclamo("Aprobado");
			
			PolizaCobertura actualizarPc = polizaCoberturaRepository.findByIdCoberturaAndIdPoliza(reclamoDto.getIdCobertura(), reclamoDto.getIdPoliza());
		    
		    BigDecimal nuevaSuma = actualizarPc.getSumaAseguradaDisponible().subtract(reclamoDto.getMontoAprobado());
			actualizarPc.setSumaAseguradaDisponible(nuevaSuma);
			polizaCoberturaRepository.save(actualizarPc);

			Reclamo reclamoGuardado = reclamoRepository.save(aprobarReclamo);

			return new ResponseEntity<>(reclamoGuardado, HttpStatus.CREATED);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Error al crear el reclamo: " + e.getMessage());
		}
	}
	
	@PostMapping("/rechazar-reclamo")
	public ResponseEntity<Reclamo> rechazarReclamo(@Validated @RequestBody EmitirReclamoDto reclamoDto) {
		try {
			Reclamo aprobarReclamo = reclamoRepository.findById(reclamoDto.getIdReclamo())
					.orElseThrow(() -> new RuntimeException("Póliza no encontrada"));

			aprobarReclamo.setPerito(reclamoDto.getPerito());
			aprobarReclamo.setObservacion(reclamoDto.getObservacion());
			aprobarReclamo.setEstadoReclamo("Rechazado");
			
		

			Reclamo reclamoGuardado = reclamoRepository.save(aprobarReclamo);

			return new ResponseEntity<>(reclamoGuardado, HttpStatus.CREATED);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Error al crear el reclamo: " + e.getMessage());
		}
	}
	
	
}