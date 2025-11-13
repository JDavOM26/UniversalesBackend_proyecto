package com.universales.gestionseguros.controller;

import com.universales.gestionseguros.dto.OkResponseDto;
import com.universales.gestionseguros.dto.PerfilDto;
import com.universales.gestionseguros.entity.Usuario;
import com.universales.gestionseguros.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/noauth/perfil")
public class PerfilController {
    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;

    public PerfilController(UsuarioRepository usuarioRepository, ModelMapper modelMapper) {
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
    }

    @PostMapping(path = "/imagen", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<OkResponseDto> actualizarImagen(@RequestParam Integer id, @RequestPart("imagen") MultipartFile imagenArchivo) {

        if (imagenArchivo == null || imagenArchivo.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La imagen es obligatoria");
        }

        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        try {
            String base64 = java.util.Base64.getEncoder().encodeToString(imagenArchivo.getBytes());
            u.setImagen(base64);
            usuarioRepository.save(u);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar imagen: " + e.getMessage());
        }
    }
    
    @GetMapping(path = "/obtener")
    public ResponseEntity<PerfilDto> verPerfil (@RequestParam Integer id){
        Usuario perfil = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));        
        return ResponseEntity.ok(modelMapper.map(perfil, PerfilDto.class));
    }
} 
