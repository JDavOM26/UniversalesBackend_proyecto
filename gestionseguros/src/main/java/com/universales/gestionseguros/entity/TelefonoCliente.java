package com.universales.gestionseguros.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "telefono_cliente")
@Data
public class TelefonoCliente implements Serializable {

    private static final long serialVersionUID = -8279728844461312617L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "telefono_cliente_sq")
    @SequenceGenerator(name = "telefono_cliente_sq", sequenceName = "telefono_cliente_sq", allocationSize = 1)
    @Column(name = "id_telefono")
    private Integer idTelefono;

    @Column(name = "telefono")
    private String telefono;
    
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;
}
