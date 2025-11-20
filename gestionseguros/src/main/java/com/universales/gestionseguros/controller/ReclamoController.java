package com.universales.gestionseguros.controller;

import java.math.BigDecimal;
import java.util.Date;
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
import com.universales.gestionseguros.dto.OkResponseDto;
import com.universales.gestionseguros.dto.ReclamoResponseDto;
import com.universales.entity.PolizaCobertura;
import com.universales.entity.Reclamo;
import com.universales.gestionseguros.repository.PolizaCoberturaRepository;
import com.universales.gestionseguros.repository.ReclamoRepository;

@RestController
@RequestMapping("/api/auth/reclamos")
public class ReclamoController {

	private final ReclamoRepository reclamoRepository;
	private final PolizaCoberturaRepository polizaCoberturaRepository;

	public ReclamoController(ReclamoRepository reclamoRepository, PolizaCoberturaRepository polizaCoberturaRepository) {
		this.reclamoRepository = reclamoRepository;
		this.polizaCoberturaRepository = polizaCoberturaRepository;
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
	public ResponseEntity<List<Reclamo>> obtenerReclamoPorUsuario(@RequestParam Integer ajustador) {
		try {
			return ResponseEntity.ok(reclamoRepository.findByAjustador(ajustador));
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}

	}

	@GetMapping("/obtener-reclamos-ingresados")
	public ResponseEntity<List<ReclamoResponseDto>> obtenerReclamosIngresados() {
		try {
			String estadoReclamo = "Ingresado";
			List<Reclamo> reclamos = reclamoRepository.findByEstadoReclamo(estadoReclamo);
			List<ReclamoResponseDto> dtos = reclamos.stream().map(reclamo -> {
				ReclamoResponseDto dto = new ReclamoResponseDto();

				dto.setIdPoliza(reclamo.getIdPoliza());
				dto.setIdCobertura(reclamo.getIdCobertura());
				dto.setFechaSiniestro(reclamo.getFechaSiniestro());
				dto.setFechaIngresoReclamo(reclamo.getFechaIngresoReclamo());
				dto.setIdReclamo(reclamo.getIdReclamo());
				dto.setPrimerNombre(reclamo.getPrimerNombre());
				dto.setSegundoNombre(reclamo.getSegundoNombre());
				dto.setTercerNombre(reclamo.getTercerNombre());
				dto.setApellidoPrimero(reclamo.getApellidoPrimero());
				dto.setApellidoSegundo(reclamo.getApellidoSegundo());
				dto.setApellidoTercero(reclamo.getApellidoTercero());
				dto.setAjustador(reclamo.getAjustador());
				dto.setEstadoReclamo(reclamo.getEstadoReclamo());

				PolizaCobertura polizaCobertura = polizaCoberturaRepository
						.findByIdCoberturaAndIdPoliza(reclamo.getIdCobertura(), reclamo.getIdPoliza());

				dto.setSumaAseguradaDisponible(
						polizaCobertura != null ? polizaCobertura.getSumaAseguradaDisponible() : null);

				return dto;
			}).toList();

			return ResponseEntity.ok(dtos);

		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

	@PostMapping("/aprobar-reclamo")
	public ResponseEntity<OkResponseDto> aprobarReclamo(@Validated @RequestBody EmitirReclamoDto reclamoDto) {
		try {
			Reclamo aprobarReclamo = reclamoRepository.findById(reclamoDto.getIdReclamo())
					.orElseThrow(() -> new RuntimeException("Póliza no encontrada"));

			aprobarReclamo.setPerito(reclamoDto.getPerito());
			aprobarReclamo.setMontoAprobado(reclamoDto.getMontoAprobado());
			aprobarReclamo.setEstadoReclamo("Aprobado");
			aprobarReclamo.setFechaDecisionPerito(new Date());
			PolizaCobertura actualizarPc = polizaCoberturaRepository
					.findByIdCoberturaAndIdPoliza(reclamoDto.getIdCobertura(), reclamoDto.getIdPoliza());

			BigDecimal nuevaSuma = actualizarPc.getSumaAseguradaDisponible().subtract(reclamoDto.getMontoAprobado());
			actualizarPc.setSumaAseguradaDisponible(nuevaSuma);
			polizaCoberturaRepository.save(actualizarPc);

			Reclamo reclamoGuardado = reclamoRepository.save(aprobarReclamo);

			OkResponseDto response = new OkResponseDto();
			response.setResponse(String.format("Reclamo con ID %d emitido con exito", reclamoGuardado.getIdReclamo()));

			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Error al crear el reclamo: " + e.getMessage());
		}
	}

	@PostMapping("/rechazar-reclamo")
	public ResponseEntity<OkResponseDto> rechazarReclamo(@Validated @RequestBody EmitirReclamoDto reclamoDto) {
		try {
			Reclamo rechazarReclamo = reclamoRepository.findById(reclamoDto.getIdReclamo())
					.orElseThrow(() -> new RuntimeException("Póliza no encontrada"));

			rechazarReclamo.setPerito(reclamoDto.getPerito());
			rechazarReclamo.setObservacion(reclamoDto.getObservacion());
			rechazarReclamo.setEstadoReclamo("Rechazado");
			rechazarReclamo.setFechaDecisionPerito(new Date());

			Reclamo reclamoGuardado = reclamoRepository.save(rechazarReclamo);

			OkResponseDto response = new OkResponseDto();
			response.setResponse(String.format("Reclamo con ID %d emitido con exito", reclamoGuardado.getIdReclamo()));

			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Error al crear el reclamo: " + e.getMessage());
		}
	}

}