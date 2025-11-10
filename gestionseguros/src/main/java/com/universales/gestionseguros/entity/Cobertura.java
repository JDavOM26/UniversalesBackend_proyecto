package com.universales.gestionseguros.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "COBERTURA")
@Data
public class Cobertura implements Serializable {

	
	private static final long serialVersionUID = 3091058784543287506L;
	@Id
	@Column(name = "id_cobertura")
	@SequenceGenerator(name = "cobertura_sq", sequenceName = "cobertura_sq", allocationSize = 1)

	@GeneratedValue(generator = "cobertura_sq", strategy = GenerationType.SEQUENCE)
	private Integer idCobertura;

	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "estado")
	private String estado;

	@Column(name = "suma_asegurada")
	private BigDecimal sumaAsegurada;

	@Column(name = "gasto_emision")
	private BigDecimal gastoEmision;

	@Column(name = "comision_venta")
	private BigDecimal comisionVenta;

	@Column(name = "prima_neta")
	private BigDecimal primaNeta;

	@Column(name = "prima_total_sin_iva")
	private BigDecimal primaTotalSinIva;
	
	@Column(name = "prima_total_con_iva")
	private BigDecimal primaTotalConIva;
}
