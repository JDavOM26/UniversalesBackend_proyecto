package com.universales.gestionseguros.dto;

import lombok.Data;

@Data
public class LoginResponseDto {
	private String jwt;
	private Integer idUsuario;
	private String rol;


}
