package com.universales.gestionseguros.dto;


import java.util.List;

import lombok.Data;

@Data
public class PaqueteDto {
	private String nombre;
	private List<Integer> idCobertura;
}
