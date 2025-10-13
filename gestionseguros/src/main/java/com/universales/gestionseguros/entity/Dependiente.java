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

    @Column(name = "fecha_nacimiento", nullable = false)
    private Date fechaNacimiento;

    @Column(name = "parentesco", length = 50, nullable = false)
    private String parentesco;

    @ManyToOne
    @JoinColumn(name = "id_poliza", nullable = false)
    private Poliza poliza;
}
