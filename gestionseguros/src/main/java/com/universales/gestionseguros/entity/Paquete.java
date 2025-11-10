package com.universales.gestionseguros.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.*;

import lombok.Data;

@Entity
@Table(name = "paquete")
@Data
public class Paquete implements Serializable {

    private static final long serialVersionUID = 3121683810140027897L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "paquete_sq")
    @SequenceGenerator(name = "paquete_sq", sequenceName = "paquete_sq", allocationSize = 1)
    @Column(name = "id_paquete")
    private Integer idPaquete;

    @Column(name = "nombre")
    private String nombre;
    
    @Column(name = "estado")
	private String estado;

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
