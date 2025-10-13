package com.universales.gestionseguros.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ClienteDto {
	  private String primerNombre;
	    private String segundoNombre;
	    private String apellidoPrimero;
	    private String apellidoSegundo;
	    private String nit;
	    
	    @JsonFormat(pattern = "dd-MM-yyyy")
	    private Date fechaNacimiento;
	    private String dpi;
	    private String genero;

	    private List<String> telefonos;
	    private List<String> correos;
	    private List<DireccionDto> direcciones;
}
