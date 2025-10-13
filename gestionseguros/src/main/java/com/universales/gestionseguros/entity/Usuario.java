package com.universales.gestionseguros.entity;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "USUARIO")
@Data
public class Usuario implements Serializable{
	private static final long serialVersionUID = 8570838120115310876L;

	@Id
    @Column(name="id_usuario")
	@SequenceGenerator(
			name = "usuario_sq",
			sequenceName="usuario_sq",
			allocationSize = 1
			)
	
	@GeneratedValue(generator = "usuario_sq", strategy=GenerationType.SEQUENCE)
    private Integer idUsuario;

	    @Column(name="username", nullable = false, length = 50)
	    private String username;

	    @Column(name="password", nullable = false, length = 255)
	    private String password;
	    
	    @Column(name = "rol", nullable = false, length = 50)
	    private String rol;
	  	    
}

