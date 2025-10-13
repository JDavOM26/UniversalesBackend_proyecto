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

    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;

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
