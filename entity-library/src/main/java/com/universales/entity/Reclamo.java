package com.universales.entity;

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

    @Column(name = "id_cobertura")
    private Integer idCobertura;

    @Column(name = "id_poliza")
    private Integer idPoliza;
    

    @Column(name = "estado_reclamo")
    private String estadoReclamo;

    @Column(name = "ajustador")
    private Integer ajustador;

    @Column(name = "perito")
    private Integer perito;

    @Column(name = "monto_aprobado")
    private BigDecimal montoAprobado;

    @Column(name = "observacion")
    private String observacion;

    @Column(name = "fecha_siniestro")
    private Date fechaSiniestro;

    @Column(name = "fecha_ingreso_reclamo")
    private Date fechaIngresoReclamo;
    

    
    @Column(name = "fecha_decision_perito")
    private Date fechaDecisionPerito;
   
    @Column(name = "primer_nombre")
    private String primerNombre;

    @Column(name = "segundo_nombre")
    private String segundoNombre;

    @Column(name = "tercer_nombre")
    private String tercerNombre;

    @Column(name = "apellido_primero")
    private String apellidoPrimero;

    @Column(name = "apellido_segundo")
    private String apellidoSegundo;

    @Column(name = "apellido_tercero")
    private String apellidoTercero;

}
