package com.universales.gestionseguros.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor 

public class PolizaPaginadoDto {

	private Integer idPoliza;
	private String nombrePoliza;
	private Integer contratante;
	private BigDecimal primaVendidaTotal;
	private String nombreContratante;
	private String estado;
	private Integer vendedor;
	private Integer idPaquete;
	private Date fechaCreacion;
	private Date fechaVencimiento;
	
	
	}