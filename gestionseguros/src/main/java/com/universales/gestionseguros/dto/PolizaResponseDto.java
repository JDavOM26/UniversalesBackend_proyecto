package com.universales.gestionseguros.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class PolizaResponseDto {
	private Integer idPoliza;
	private String nombrePoliza;
	private Integer idContratante;
	private String nombreContratante;
	private String estado;
	private Integer vendedor;
	private Integer idPaquete;
	private Date fechaCreacion;
	private Date fechaVencimiento;
}
