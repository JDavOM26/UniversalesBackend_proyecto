package com.universales.gestionseguros.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.universales.gestionseguros.dto.LoginResponseDto;
import com.universales.gestionseguros.entity.Usuario;
import com.universales.gestionseguros.repository.UsuarioRepository;
import com.universales.gestionseguros.security.JwtTokenUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@RestController
@CrossOrigin
@RequestMapping("/api/noauth")
public class JwtAuthenticationController {
	private final AuthenticationManager authenticationManager;
	private final UsuarioRepository userRepository;
	private final JwtTokenUtil jwtUtils;

	public JwtAuthenticationController(AuthenticationManager authenticationManager, UsuarioRepository userRepository, JwtTokenUtil jwtUtils) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.jwtUtils = jwtUtils;
	}

	private static final Log LOG = LogFactory.getLog(JwtAuthenticationController.class);

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> authenticateUser(@RequestBody Usuario user) {
		try {
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			String jwt = jwtUtils.generateToken(userDetails.getUsername());

			Usuario usuario = userRepository.findByUsername(user.getUsername());

			LoginResponseDto response = new LoginResponseDto();
			response.setJwt(jwt);
			response.setIdUsuario(usuario.getIdUsuario());
			response.setRol(usuario.getRol());

			return ResponseEntity.ok(response);

		} catch (BadCredentialsException e) {
			LOG.info(e);
			throw e;
		}
	}
}