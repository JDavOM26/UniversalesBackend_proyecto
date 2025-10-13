package com.universales.gestionseguros.dto;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
public class EmitirReclamoDto {
	private Integer idCobertura;
	private Integer idPoliza;
	private Date fechaSiniestro;
	private Date fechaIngresoReclamo;
	private Integer idReclamo;
	private BigDecimal montoAprobado;
	private Integer perito;
	private String observacion;

	private String primerNombre;
	private String segundoNombre;
	private String tercerNombre;
	private String apellidoPrimero;
	private String apellidoSegundo;
	private String apellidoTercero;
	private Integer ajustador;
}
