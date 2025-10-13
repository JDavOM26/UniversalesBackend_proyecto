package com.universales.gestionseguros.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "cliente")
@Data
public class Cliente implements Serializable {

    private static final long serialVersionUID = 3657598652227155955L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cliente_sq")
    @SequenceGenerator(name = "cliente_sq", sequenceName = "cliente_sq", allocationSize = 1)
    @Column(name = "id_cliente")
    private Integer idCliente;

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

    @Column(name = "nit", length = 50, nullable = false, unique = true)
    private String nit;

    @Column(name = "fecha_nacimiento", nullable = false)
    private Date fechaNacimiento;

    @Column(name = "dpi", length = 50, nullable = false, unique = true)
    private String dpi;

    @Column(name = "genero", length = 50, nullable = false)
    private String genero;
    
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TelefonoCliente> telefonos = new ArrayList<>();

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CorreoCliente> correos = new ArrayList<>();

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClienteDireccion> direcciones = new ArrayList<>();
}
