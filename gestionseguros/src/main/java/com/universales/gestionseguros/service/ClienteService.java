package com.universales.gestionseguros.service;

import org.springframework.stereotype.Service;
import com.universales.gestionseguros.dto.ClienteDto;
import com.universales.entity.Cliente;
import com.universales.entity.ClienteDireccion;
import com.universales.entity.CorreoCliente;
import com.universales.entity.TelefonoCliente;
import com.universales.gestionseguros.repository.ClienteRepository;

@Service
public class ClienteService {
	private final ClienteRepository clienteRepository;

	public ClienteService(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	public Integer crearCliente(ClienteDto clienteDto) {

		Cliente clienteExistente = clienteRepository.findByNit(clienteDto.getNit());
		if (clienteExistente == null) {
			clienteExistente = clienteRepository.findByDpi(clienteDto.getDpi());
		}

		if (clienteExistente != null) {
			return clienteExistente.getIdCliente();
		}

		Cliente cliente = new Cliente();
		cliente.setPrimerNombre(clienteDto.getPrimerNombre());
		cliente.setSegundoNombre(clienteDto.getSegundoNombre());
		cliente.setApellidoPrimero(clienteDto.getApellidoPrimero());
		cliente.setApellidoSegundo(clienteDto.getApellidoSegundo());
		cliente.setNit(clienteDto.getNit());
		cliente.setFechaNacimiento(clienteDto.getFechaNacimiento());
		cliente.setDpi(clienteDto.getDpi());
		cliente.setGenero(clienteDto.getGenero());

		if (clienteDto.getTelefonos() != null) {
			clienteDto.getTelefonos().forEach(tel -> {
				TelefonoCliente telefono = new TelefonoCliente();
				telefono.setTelefono(tel);
				telefono.setCliente(cliente);
				cliente.getTelefonos().add(telefono);
			});
		}

		if (clienteDto.getCorreos() != null) {
			clienteDto.getCorreos().forEach(mail -> {
				CorreoCliente correo = new CorreoCliente();
				correo.setEmail(mail);
				correo.setCliente(cliente);
				cliente.getCorreos().add(correo);
			});
		}

		if (clienteDto.getDirecciones() != null) {
			clienteDto.getDirecciones().forEach(dirDto -> {
				ClienteDireccion direccion = new ClienteDireccion();
				direccion.setDireccion(dirDto.getDireccion());
				direccion.setTipoDireccion(dirDto.getTipoDireccion());
				direccion.setCliente(cliente);
				cliente.getDirecciones().add(direccion);
			});
		}

		Cliente savedCliente = clienteRepository.save(cliente);

		return savedCliente.getIdCliente();
	}
}
