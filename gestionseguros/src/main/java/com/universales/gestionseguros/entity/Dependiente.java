package com.universales.gestionseguros.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "dependiente")
@Data
public class Dependiente implements Serializable {

    private static final long serialVersionUID = 2854005761154105709L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dependiente_sq")
    @SequenceGenerator(name = "dependiente_sq", sequenceName = "dependiente_sq", allocationSize = 1)
    @Column(name = "id_dependiente")
    private Integer idDependiente;

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

    @Column(name = "fecha_nacimiento")
    private Date fechaNacimiento;

    @Column(name = "parentesco")
    private String parentesco;

    @ManyToOne
    @JoinColumn(name = "id_poliza")
    private Poliza poliza;
}
