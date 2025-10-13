package com.universales.gestionseguros.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.universales.gestionseguros.entity.Cliente;
import com.universales.gestionseguros.repository.ClienteRepository;

@RestController
@RequestMapping("/api/auth/clientes")
public class ClienteController {

	private final ClienteRepository clienteRepository;

	public ClienteController(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	@GetMapping("/buscar-cliente")
	public ResponseEntity<Cliente> encontrarClientePorDocumento(@RequestParam String documento,
			@RequestParam String tipoDocumento) {

		if (tipoDocumento.equals("nit")) {
			Cliente existingCliente = clienteRepository.findByNit(documento);
			return ResponseEntity.ok(existingCliente);

		}

		if (tipoDocumento.equals("dpi")) {
			Cliente existingCliente = clienteRepository.findByDpi(documento);
			return ResponseEntity.ok(existingCliente);

		}
		return ResponseEntity.notFound().build();
	}
}