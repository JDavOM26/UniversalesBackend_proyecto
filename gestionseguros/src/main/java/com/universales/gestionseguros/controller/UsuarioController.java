package com.universales.gestionseguros.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.universales.gestionseguros.dto.OkResponseDto;
import com.universales.gestionseguros.dto.UsuarioDto;
import com.universales.gestionseguros.entity.Usuario;
import com.universales.gestionseguros.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/auth/usuarios")
public class UsuarioController {

	private final UsuarioRepository usuarioRepository;
	private final PasswordEncoder passwordEncoder;

	public UsuarioController(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
		this.usuarioRepository = usuarioRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@PostMapping("/crear-usuario")
	public ResponseEntity<OkResponseDto> crearUsuario(@Validated @RequestBody UsuarioDto usuarioDto) {
	    try {
	    	OkResponseDto response = new OkResponseDto();
	        Usuario newUser = new Usuario();
	        newUser.setUsername(usuarioDto.getUsername());
	        newUser.setPassword(passwordEncoder.encode(usuarioDto.getPassword()));
	        newUser.setRol(usuarioDto.getRol());

	        Usuario savedUser = usuarioRepository.save(newUser);
	        response.setResponse(String.format("Usuario con ID %d creado con exito", savedUser.getIdUsuario()));
			return new ResponseEntity<>(response, HttpStatus.CREATED);
	    } catch (Exception e) {
	      
	        throw new ResponseStatusException(
	            HttpStatus.INTERNAL_SERVER_ERROR,
	            "Error al crear el usuario: " + e.getMessage()
	        );
	    }
	}
}
