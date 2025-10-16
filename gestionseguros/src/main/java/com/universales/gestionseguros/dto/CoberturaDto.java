package com.universales.gestionseguros.dto;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class CoberturaDto {

	private String nombre;
	private BigDecimal sumaAsegurada;
	private BigDecimal gastoEmision;
	private BigDecimal comisionVenta;
	private BigDecimal primaNeta;
	private BigDecimal primaTotalConIva;
	private BigDecimal primaTotalSinIva;
	
}
