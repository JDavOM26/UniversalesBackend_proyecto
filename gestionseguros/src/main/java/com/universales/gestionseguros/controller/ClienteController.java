package com.universales.gestionseguros.controller;

import org.hibernate.Hibernate;
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
	public ResponseEntity<Cliente> buscarCliente(@RequestParam String valor) {
		try {
			Cliente cliente = clienteRepository.buscarPorDpiONit(valor);

			if (cliente == null) {
				return ResponseEntity.noContent().build();
			}

			Hibernate.initialize(cliente.getTelefonos());
			Hibernate.initialize(cliente.getCorreos());
			Hibernate.initialize(cliente.getDirecciones());

			return ResponseEntity.ok(cliente);

		} catch (Exception e) {
			
			return ResponseEntity.status(500).body(null); 
		}
	}

}