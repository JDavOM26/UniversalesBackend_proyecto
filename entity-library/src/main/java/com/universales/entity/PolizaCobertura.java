package com.universales.entity;

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

    @Column(name="id_poliza")
    private Integer idPoliza;

    @Column(name="id_cobertura")
    private Integer idCobertura;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "suma_asegurada_disponible")
    private BigDecimal sumaAseguradaDisponible;
    
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
