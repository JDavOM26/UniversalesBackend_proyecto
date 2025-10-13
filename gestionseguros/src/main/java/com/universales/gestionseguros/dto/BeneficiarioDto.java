package com.universales.gestionseguros.dto;

import lombok.Data;

@Data
public class BeneficiarioDto {
private Integer idBeneficiario;
private String primerNombre;
private String segundoNombre;
private String tercerNombre;
private String apellidoPrimero;
private String apellidoSegundo;
private String apellidoTercero;
private Double participacion;
private String genero;
private String parentesco;
private Integer idPoliza; 
}