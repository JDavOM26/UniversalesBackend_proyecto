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
	public ResponseEntity<Usuario> crearUsuario(@Validated @RequestBody UsuarioDto usuarioDto) {
	    try {
	        Usuario newUser = new Usuario();
	        newUser.setUsername(usuarioDto.getUsername());
	        newUser.setPassword(passwordEncoder.encode(usuarioDto.getPassword()));
	        newUser.setRol(usuarioDto.getRol());

	        Usuario savedUser = usuarioRepository.save(newUser);

	        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
	    } catch (Exception e) {
	      
	        throw new ResponseStatusException(
	            HttpStatus.INTERNAL_SERVER_ERROR,
	            "Error al crear el usuario: " + e.getMessage()
	        );
	    }
	}
}
