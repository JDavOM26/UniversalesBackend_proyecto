package com.universales.gestionseguros.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class ClienteResponseDto {
	private String primerNombre;
    private String segundoNombre;
    private String tercerNombre;
    private String apellidoPrimero;
    private String apellidoSegundo;
    private String apellidoTercero;
    private String nit; 
    private Date fechaNacimiento;
    private String dpi;
    private String genero;
    private List<TelefonoDto> telefonos;
    private List<CorreoDto> correos;
    private List<DireccionDto> direcciones;

}
