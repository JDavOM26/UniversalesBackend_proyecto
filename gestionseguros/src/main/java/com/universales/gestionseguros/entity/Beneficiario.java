package com.universales.gestionseguros.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "BENEFICIARIO")
@Data
public class Beneficiario implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9130863321022548377L;

	@Id
    @Column(name="id_beneficiario")
	@SequenceGenerator(
			name = "beneficiario_sq",
			sequenceName="beneficiario_sq",
			allocationSize = 1
			)
	
	@GeneratedValue(generator = "beneficiario_sq", strategy=GenerationType.SEQUENCE)
    private Integer idBeneficiario;

	    @Column(name="primer_nombre",length = 50, nullable = false)
	    private String primerNombre;
	    
	    @Column(name="segundo_nombre", length = 50, nullable = true)
	    private String segundoNombre;
	    
	    @Column(name="tercer_nombre",length = 50, nullable = true)
	    private String tercerNombre;
	    
	    @Column(name="apellido_primero",length = 50, nullable = false)
	    private String apellidoPrimero;
	    
	    @Column(name="apellido_segundo",length = 50, nullable = true)
	    private String apellidoSegundo;
	    
	    @Column(name="apellido_tercero",length = 50, nullable = true)
	    private String apellidoTercero;
	    
	    @Column(name="participacion", length = 50, nullable = false)
	    private Double participacion;
	    
	    @Column(name="genero", nullable = false)
	    private String genero;
	    
	    @Column(name="parentesco", nullable = false)
	    private String parentesco;
	    
	    @ManyToOne
	    @JoinColumn(name = "id_poliza", nullable = false)
	    private Poliza poliza;
}
