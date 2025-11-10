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
import com.universales.gestionseguros.dto.OkResponseDto;
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
	public ResponseEntity<List<Paquete>> obtenerPaquetes() {
		try {
			return ResponseEntity.ok(paqueteRepository.findAll());
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

	@PostMapping("/crear-paquete")
	public ResponseEntity<OkResponseDto> crearPaquete(@Validated @RequestBody PaqueteDto paqueteDto) {
		OkResponseDto response = new OkResponseDto();

		try {
			if (paqueteDto.getIdPaquete() != null) {

				Paquete paquete = paqueteRepository.findById(paqueteDto.getIdPaquete())
						.orElseThrow(() -> new RuntimeException("Paquete no encontrado"));

				paquete.setNombre(paqueteDto.getNombre());
				paquete.setPrimaNeta(paqueteDto.getPrimaNeta());
				paquete.setComisionVenta(paqueteDto.getComisionVenta());
				paquete.setGastoEmision(paqueteDto.getGastoEmision());
				paquete.setPrimaTotalSinIva(paqueteDto.getPrimaTotalSinIva());
				paquete.setPrimaTotalConIva(paqueteDto.getPrimaTotalConIva());
				paquete.setEstado(paqueteDto.getEstado());

				Paquete updatedPaquete = paqueteRepository.save(paquete);
				response.setResponse(
						String.format("Paquete con ID %d actualizado con éxito", updatedPaquete.getIdPaquete()));

				return new ResponseEntity<>(response, HttpStatus.OK);
			}

			Paquete newPaquete = new Paquete();
			newPaquete.setNombre(paqueteDto.getNombre());
			newPaquete.setPrimaNeta(paqueteDto.getPrimaNeta());
			newPaquete.setComisionVenta(paqueteDto.getComisionVenta());
			newPaquete.setGastoEmision(paqueteDto.getGastoEmision());
			newPaquete.setPrimaTotalSinIva(paqueteDto.getPrimaTotalSinIva());
			newPaquete.setPrimaTotalConIva(paqueteDto.getPrimaTotalConIva());
			newPaquete.setEstado(paqueteDto.getEstado() != null ? paqueteDto.getEstado() : "Activa");

			Paquete savedPaquete = paqueteRepository.save(newPaquete);

			if (paqueteDto.getIdCobertura() != null) {
				paqueteCoberturaService.crearPaqueteCobertura(paqueteDto.getIdCobertura(), savedPaquete.getIdPaquete());
			}

			response.setResponse(String.format("Paquete con ID %d creado con éxito", savedPaquete.getIdPaquete()));

			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (RuntimeException e) {

			response.setResponse("Error: " + e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {

			response.setResponse("Error interno del servidor al procesar el paquete.");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}