package com.universales.gestionseguros.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "poliza_cobertura")
@Data
public class PolizaCobertura implements Serializable {

    private static final long serialVersionUID = -427004261306159133L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "poliza_cobertura_sq")
    @SequenceGenerator(name = "poliza_cobertura_sq", sequenceName = "poliza_cobertura_sq", allocationSize = 1)
    @Column(name = "id_poliza_cobertura")
    private Integer idPolizaCobertura;

    @Column(name="id_poliza", nullable = false)
    private Integer idPoliza;

    @Column(name="id_cobertura", nullable = false)
    private Integer idCobertura;

    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;

    @Column(name = "suma_asegurada_disponible", nullable = false)
    private BigDecimal sumaAseguradaDisponible;

    @Column(name = "gasto_emision", nullable = false)
    private BigDecimal gastoEmision;

    @Column(name = "comision_venta", nullable = false)
    private BigDecimal comisionVenta;

    @Column(name = "prima_neta", nullable = false)
    private BigDecimal primaNeta;

    @Column(name = "prima_total_sin_iva", nullable = false)
    private BigDecimal primaTotalSinIva;

    @Column(name = "prima_total_con_iva", nullable = false)
    private BigDecimal primaTotalConIva;
}
