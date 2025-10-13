package com.universales.gestionseguros.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.universales.gestionseguros.entity.Usuario;

@Repository("usuarioRepository")
public interface UsuarioRepository  extends JpaRepository<Usuario, Integer> {
	
	    Usuario findByUsername(String username);
}
