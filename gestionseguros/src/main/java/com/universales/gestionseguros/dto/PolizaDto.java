package com.universales.gestionseguros.dto;

import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class PolizaDto {
private Integer idPoliza;
private String nombrePoliza;
private Integer idContratante;
private String nombreContratante;
private String estado;
private Integer vendedor;
private Integer idPaquete;
private Date fechaCreacion;
private Date fechaVencimiento;
private ClienteDto contratante;
private List<BeneficiarioDto> beneficiarios;
private List<DependienteDto> dependientes;
}