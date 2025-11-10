package com.universales.gestionseguros.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "POLIZA")
@Data
public class Poliza implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 383020213630258247L;

	@Id
    @Column(name="id_poliza")
	@SequenceGenerator(
			name = "poliza_sq",
			sequenceName="poliza_sq",
			allocationSize = 1
			)
	
	@GeneratedValue(generator = "poliza_sq", strategy=GenerationType.SEQUENCE)
    private Integer idPoliza;

	    @Column(name="contratante")
	    private Integer contratante;
	    
	    @Column(name="nombre_poliza")
	    private String nombrePoliza;
	    
	    @Column(name="estado")
	    private String estado;

	    @Column(name="vendedor")
	    private Integer vendedor;
	    
	    @Column(name="prima_vendida_total")
	    private BigDecimal primaVendidaTotal = BigDecimal.ZERO;
	    
	    @Column(name="id_paquete")
	    private Integer idPaquete;
	    
	    @Column(name="fecha_creacion")
	    private Date fechaCreacion;
	    
	    @Column(name="fecha_vencimiento")
	    private Date fechaVencimiento;
	  
	    @OneToMany(mappedBy = "poliza", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	    @JsonBackReference
	    @JsonIgnore
	    private List<Beneficiario> beneficiarios = new ArrayList<>();

	    @OneToMany(mappedBy = "poliza", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	    @JsonBackReference
	    @JsonIgnore
	    private List<Dependiente> dependientes = new ArrayList<>();
}