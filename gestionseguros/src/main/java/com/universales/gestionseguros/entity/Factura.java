package com.universales.gestionseguros.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "factura")
@Data
public class Factura implements Serializable {

    private static final long serialVersionUID = 2349669383037998467L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "factura_sq")
    @SequenceGenerator(name = "factura_sq", sequenceName = "factura_sq", allocationSize = 1)
    @Column(name = "id_factura")
    private Integer idFactura;

    @Column(name = "id_poliza", nullable = false)
    private Integer idPoliza;

    @Column(name = "numero_factura", nullable = false)
    private Integer numeroFactura;

    @Column(name = "fecha_emision", nullable = false)
    private Date fechaEmision;

    @Column(name = "monto_total", nullable = false)
    private BigDecimal montoTotal;
}
