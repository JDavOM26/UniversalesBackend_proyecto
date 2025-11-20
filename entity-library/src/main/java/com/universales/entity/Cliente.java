package com.universales.entity;

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

    @Column(name = "nit")
    private String nit;

    @Column(name = "fecha_nacimiento")
    private Date fechaNacimiento;

    @Column(name = "dpi")
    private String dpi;

    @Column(name = "genero")
    private String genero;
    
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TelefonoCliente> telefonos = new ArrayList<>();

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CorreoCliente> correos = new ArrayList<>();

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClienteDireccion> direcciones = new ArrayList<>();
}
