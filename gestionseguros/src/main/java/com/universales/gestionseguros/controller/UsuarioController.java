package com.universales.gestionseguros.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.universales.gestionseguros.dto.OkResponseDto;
import com.universales.gestionseguros.dto.UsuarioDto;
import com.universales.entity.Usuario;
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

    @PostMapping(path = "/crear-usuario", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<OkResponseDto> crearUsuarioMultipart(@RequestPart("usuario") UsuarioDto usuarioDto, @RequestPart(value = "imagen", required = false) MultipartFile imagenArchivo) {

        try {
            OkResponseDto response = new OkResponseDto();
            Usuario newUser = new Usuario();
            newUser.setUsername(usuarioDto.getUsername());
            newUser.setPassword(passwordEncoder.encode(usuarioDto.getPassword()));
            newUser.setRol(usuarioDto.getRol());

            if (imagenArchivo != null && !imagenArchivo.isEmpty()) {
                String base64 = java.util.Base64.getEncoder().encodeToString(imagenArchivo.getBytes());
                newUser.setImagen(base64);
            }

            Usuario savedUser = usuarioRepository.save(newUser);
            response.setResponse(String.format("Usuario con ID %d creado con exito", savedUser.getIdUsuario()));
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear el usuario: " + e.getMessage());
        }
    }
    
    
}
