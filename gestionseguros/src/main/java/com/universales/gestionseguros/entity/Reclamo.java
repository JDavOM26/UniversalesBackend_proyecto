package com.universales.gestionseguros.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "reclamo")
@Data
public class Reclamo implements Serializable {

    private static final long serialVersionUID = -6202526653373937288L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reclamo_sq")
    @SequenceGenerator(name = "reclamo_sq", sequenceName = "reclamo_sq", allocationSize = 1)
    @Column(name = "id_reclamo")
    private Integer idReclamo;

    @Column(name = "id_cobertura", nullable = false)
    private Integer idCobertura;

    @Column(name = "id_poliza", nullable = false)
    private Integer idPoliza;

    @Column(name = "estado_reclamo", length = 50)
    private String estadoReclamo;

    @Column(name = "ajustador", nullable = false)
    private Integer ajustador;

    @Column(name = "perito", nullable = true)
    private Integer perito;

    @Column(name = "monto_aprobado")
    private BigDecimal montoAprobado;

    @Column(name = "observacion", length = 100, nullable = true)
    private String observacion;

    @Column(name = "fecha_siniestro", nullable = false)
    private Date fechaSiniestro;

    @Column(name = "fecha_ingreso_reclamo", nullable = false)
    private Date fechaIngresoReclamo;

   
    @Column(name = "primer_nombre", length = 50, nullable = false)
    private String primerNombre;

    @Column(name = "segundo_nombre", length = 50)
    private String segundoNombre;

    @Column(name = "tercer_nombre", length = 50)
    private String tercerNombre;

    @Column(name = "apellido_primero", length = 50, nullable = false)
    private String apellidoPrimero;

    @Column(name = "apellido_segundo", length = 50)
    private String apellidoSegundo;

    @Column(name = "apellido_tercero", length = 50)
    private String apellidoTercero;

}
