package com.universales.gestionseguros.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "correo_cliente")
@Data
public class CorreoCliente implements Serializable {

    private static final long serialVersionUID = 5422000258831531649L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "correo_cliente_sq")
    @SequenceGenerator(name = "correo_cliente_sq", sequenceName = "correo_cliente_sq", allocationSize = 1)
    @Column(name = "id_correo")
    private Integer idCorreo;

    @Column(name = "email", length = 50, nullable = false)
    private String email;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;
}
