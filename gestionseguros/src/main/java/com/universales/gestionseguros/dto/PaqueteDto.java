package com.universales.gestionseguros.dto;


import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class PaqueteDto {
	private String nombre;
	private Integer idPaquete;
	private String estado;
	private List<Integer> idCobertura;
	private BigDecimal gastoEmision;
	private BigDecimal comisionVenta;
	private BigDecimal primaNeta;
	private BigDecimal primaTotalConIva;
	private BigDecimal primaTotalSinIva;
}
