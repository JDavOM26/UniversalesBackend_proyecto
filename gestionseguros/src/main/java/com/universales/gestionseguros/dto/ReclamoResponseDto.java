package com.universales.gestionseguros.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class ReclamoResponseDto {
	private Integer idPoliza;
	private Integer idCobertura;
	private BigDecimal sumaAseguradaDisponible;
	private Date fechaSiniestro;
	private Date fechaIngresoReclamo;
	private Integer idReclamo;
	private String primerNombre;
	private String segundoNombre;
	private String tercerNombre;
	private String apellidoPrimero;
	private String apellidoSegundo;
	private String apellidoTercero;
	private String estadoReclamo;
	private Integer ajustador;
}
