package com.universales.entity;

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

    @Column(name = "id_poliza")
    private Integer idPoliza;


    @Column(name = "fecha_emision")
    private Date fechaEmision;

    @Column(name = "monto_total")
    private BigDecimal montoTotal;
}
