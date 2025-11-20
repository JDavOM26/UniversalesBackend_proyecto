package com.universales.entity;
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

	    @Column(name="primer_nombre")
	    private String primerNombre;
	    
	    @Column(name="segundo_nombre")
	    private String segundoNombre;
	    
	    @Column(name="tercer_nombre")
	    private String tercerNombre;
	    
	    @Column(name="apellido_primero")
	    private String apellidoPrimero;
	    
	    @Column(name="apellido_segundo")
	    private String apellidoSegundo;
	    
	    @Column(name="apellido_tercero")
	    private String apellidoTercero;
	    
	    @Column(name="participacion")
	    private Double participacion;
	    
	    @Column(name="genero")
	    private String genero;
	    
	    @Column(name="parentesco")
	    private String parentesco;
	    
	    @ManyToOne
	    @JoinColumn(name = "id_poliza")
	    private Poliza poliza;
}
