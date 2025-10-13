package com.universales.gestionseguros.dto;

import java.util.Date;
import lombok.Data;

@Data
public class DependienteDto {
private Integer idDependiente;
private String primerNombre;
private String segundoNombre;
private String tercerNombre;
private String apellidoPrimero;
private String apellidoSegundo;
private String apellidoTercero;
private Date fechaNacimiento;
private String parentesco;
private Integer idPoliza; 
}
