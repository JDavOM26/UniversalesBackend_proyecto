package com.universales.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "cliente_direccion")
@Data
public class ClienteDireccion implements Serializable {

    private static final long serialVersionUID = -1764521952559341305L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cliente_direccion_sq")
    @SequenceGenerator(name = "cliente_direccion_sq", sequenceName = "cliente_direccion_sq", allocationSize = 1)
    @Column(name = "id_cliente_direccion")
    private Integer idClienteDireccion;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "tipo_direccion")
    private String tipoDireccion;
}
