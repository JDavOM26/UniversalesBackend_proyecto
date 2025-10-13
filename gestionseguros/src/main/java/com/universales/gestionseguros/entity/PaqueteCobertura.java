package com.universales.gestionseguros.entity;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(
    name = "paquete_cobertura",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_paquete", "id_cobertura"})
    }
)
@Data
public class PaqueteCobertura implements Serializable {

    private static final long serialVersionUID = -1830265318939994858L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "paquete_cobertura_sq")
    @SequenceGenerator(name = "paquete_cobertura_sq", sequenceName = "paquete_cobertura_sq", allocationSize = 1)
    @Column(name = "id_paquete_cobertura")
    private Integer idPaqueteCobertura;
    
    @Column(name="id_paquete", nullable = false)
    private Integer idPaquete;

    @Column(name="id_cobertura", nullable = false)
    private Integer idCobertura;
}
