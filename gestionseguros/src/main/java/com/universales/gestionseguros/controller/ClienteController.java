package com.universales.gestionseguros.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.universales.gestionseguros.dto.ClienteResponseDto;
import com.universales.gestionseguros.dto.CorreoDto;
import com.universales.gestionseguros.dto.DireccionDto;
import com.universales.gestionseguros.dto.TelefonoDto;
import com.universales.gestionseguros.entity.Cliente;
import com.universales.gestionseguros.repository.ClienteRepository;

@RestController
@RequestMapping("/api/auth/clientes")
public class ClienteController {

	private final ClienteRepository clienteRepository;

	private static final Log LOG = LogFactory.getLog(ClienteController.class);

	public ClienteController(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	@GetMapping("/buscar-cliente")
	public ResponseEntity<ClienteResponseDto> buscarCliente(@RequestParam String valor) {
		try {
			Cliente cliente = clienteRepository.buscarPorDpiONit(valor);
			if (cliente == null) {
				return ResponseEntity.noContent().build();
			}

			
			ClienteResponseDto dto = new ClienteResponseDto();
			dto.setPrimerNombre(cliente.getPrimerNombre());
			dto.setSegundoNombre(cliente.getSegundoNombre());
			dto.setApellidoPrimero(cliente.getApellidoPrimero());
			dto.setApellidoSegundo(cliente.getApellidoSegundo());
			dto.setNit(cliente.getNit());
			dto.setFechaNacimiento(cliente.getFechaNacimiento());
			dto.setDpi(cliente.getDpi());
			dto.setGenero(cliente.getGenero());

			dto.setTelefonos(cliente.getTelefonos().stream().map(t -> {
				TelefonoDto tel = new TelefonoDto();
				tel.setTelefono(t.getTelefono());
				return tel;
			}).toList());

			
			dto.setCorreos(cliente.getCorreos().stream().map(c -> {
				CorreoDto correo = new CorreoDto();
				correo.setEmail(c.getEmail()); 
				return correo;
			}).toList());

			dto.setDirecciones(cliente.getDirecciones().stream().map(d -> {
				DireccionDto dirDto = new DireccionDto();
				dirDto.setDireccion(d.getDireccion());
				dirDto.setTipoDireccion(d.getTipoDireccion());
				return dirDto;
			}).toList());

			return ResponseEntity.ok(dto);

		} catch (Exception e) {
			LOG.error("Error al buscar cliente", e);
			return ResponseEntity.status(500).build();
		}
	}

}