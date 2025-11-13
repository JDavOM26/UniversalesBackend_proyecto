package com.universales.gestionseguros.service;

import java.util.Date;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.universales.gestionseguros.dto.OkResponseDto;
import com.universales.gestionseguros.dto.PolizaDto;
import com.universales.gestionseguros.entity.Poliza;
import com.universales.gestionseguros.entity.PolizaCobertura;
import com.universales.gestionseguros.entity.Beneficiario;
import com.universales.gestionseguros.entity.Cobertura;
import com.universales.gestionseguros.entity.Dependiente;
import com.universales.gestionseguros.entity.Factura;
import com.universales.gestionseguros.entity.Paquete;
import com.universales.gestionseguros.entity.PaqueteCobertura;
import com.universales.gestionseguros.repository.CoberturaRepository;
import com.universales.gestionseguros.repository.FacturaRepository;
import com.universales.gestionseguros.repository.PaqueteCoberturaRepository;
import com.universales.gestionseguros.repository.PaqueteRepository;
import com.universales.gestionseguros.repository.PolizaCoberturaRepository;
import com.universales.gestionseguros.repository.PolizaRepository;

@Service
public class EmitirPolizaService {

	private final PolizaRepository polizaRepository;
	private final FacturaRepository facturaRepository;
	private final CoberturaRepository coberturaRepository;
	private final PaqueteCoberturaRepository paqueteCoberturaRepository;
	private final PolizaCoberturaRepository polizaCoberturaRepository;
	private final PaqueteRepository paqueteRepository;
	private final ClienteService clienteService;

	public EmitirPolizaService(PolizaRepository polizaRepository, ClienteService clienteService,
			FacturaRepository facturaRepository, CoberturaRepository coberturaRepository,
			PolizaCoberturaRepository polizaCoberturaRepository, PaqueteCoberturaRepository paqueteCoberturaRepository,
			PaqueteRepository paqueteRepository) {
		this.polizaRepository = polizaRepository;
		this.clienteService = clienteService;
		this.facturaRepository = facturaRepository;
		this.coberturaRepository = coberturaRepository;
		this.polizaCoberturaRepository = polizaCoberturaRepository;
		this.paqueteCoberturaRepository = paqueteCoberturaRepository;
		this.paqueteRepository = paqueteRepository;
	}

	@Transactional
	public OkResponseDto emitirPoliza(PolizaDto polizaDto) {
		try {
			Integer clienteId = clienteService.crearCliente(polizaDto.getContratante());

			Paquete paquete = paqueteRepository.findById(polizaDto.getIdPaquete())
					.orElseThrow(() -> new RuntimeException("paquete no encontrado"));

			Poliza poliza = new Poliza();
			poliza.setNombrePoliza(polizaDto.getNombrePoliza());
			poliza.setContratante(clienteId);
			poliza.setVendedor(polizaDto.getVendedor());
			poliza.setIdPaquete(polizaDto.getIdPaquete());
			poliza.setFechaCreacion(polizaDto.getFechaCreacion());
			poliza.setFechaVencimiento(polizaDto.getFechaVencimiento());
			poliza.setEstado("activa");
			poliza.setPrimaVendidaTotal(paquete.getPrimaTotalConIva());

			if (polizaDto.getBeneficiarios() != null) {
				for (var b : polizaDto.getBeneficiarios()) {
					Beneficiario beneficiario = new Beneficiario();
					beneficiario.setPrimerNombre(b.getPrimerNombre());
					beneficiario.setSegundoNombre(b.getSegundoNombre());
					beneficiario.setTercerNombre(b.getTercerNombre());
					beneficiario.setApellidoPrimero(b.getApellidoPrimero());
					beneficiario.setApellidoSegundo(b.getApellidoSegundo());
					beneficiario.setApellidoTercero(b.getApellidoTercero());
					beneficiario.setParticipacion(b.getParticipacion());
					beneficiario.setGenero(b.getGenero());
					beneficiario.setParentesco(b.getParentesco());
					beneficiario.setPoliza(poliza);
					poliza.getBeneficiarios().add(beneficiario);
				}
			}

			if (polizaDto.getDependientes() != null) {
				for (var d : polizaDto.getDependientes()) {
					Dependiente dependiente = new Dependiente();
					dependiente.setPrimerNombre(d.getPrimerNombre());
					dependiente.setSegundoNombre(d.getSegundoNombre());
					dependiente.setTercerNombre(d.getTercerNombre());
					dependiente.setApellidoPrimero(d.getApellidoPrimero());
					dependiente.setApellidoSegundo(d.getApellidoSegundo());
					dependiente.setApellidoTercero(d.getApellidoTercero());
					dependiente.setFechaNacimiento(d.getFechaNacimiento());
					dependiente.setParentesco(d.getParentesco());
					dependiente.setPoliza(poliza);
					poliza.getDependientes().add(dependiente);
				}
			}

			Poliza polizaGuardada = polizaRepository.save(poliza);

			List<PaqueteCobertura> coberturas = paqueteCoberturaRepository.findByIdPaquete(polizaDto.getIdPaquete());
			if (coberturas != null) {
				for (PaqueteCobertura paqueteCobertura : coberturas) {
					Cobertura cobertura = coberturaRepository.findById(paqueteCobertura.getIdCobertura())
							.orElseThrow(() -> new RuntimeException("Cobertura no encontrada"));
					PolizaCobertura newPolizaC = new PolizaCobertura();
					newPolizaC.setIdCobertura(cobertura.getIdCobertura());
					newPolizaC.setIdPoliza(polizaGuardada.getIdPoliza());
					newPolizaC.setNombre(cobertura.getNombre());
					newPolizaC.setSumaAseguradaDisponible(cobertura.getSumaAsegurada());
					newPolizaC.setSumaAsegurada(cobertura.getSumaAsegurada());
					newPolizaC.setGastoEmision(cobertura.getGastoEmision());
					newPolizaC.setComisionVenta(cobertura.getComisionVenta());
					newPolizaC.setPrimaNeta(cobertura.getPrimaNeta());
					newPolizaC.setPrimaTotalSinIva(cobertura.getPrimaTotalSinIva());
					newPolizaC.setPrimaTotalConIva(cobertura.getPrimaTotalConIva());
					polizaCoberturaRepository.save(newPolizaC);
				}
			}

			crearFactura(polizaGuardada);

			OkResponseDto response = new OkResponseDto();
			response.setResponse(String.format("Poliza con ID %d creada con exito", polizaGuardada.getIdPoliza()));
			return response;

		} catch (DataAccessException e) {
			throw new DataAccessException("Error") {
            };
		}
	}

	private void crearFactura(Poliza polizaGuardada) {
		try {
			Factura nuevaFactura = new Factura();
			nuevaFactura.setIdPoliza(polizaGuardada.getIdPoliza());
			nuevaFactura.setFechaEmision(new Date());
			nuevaFactura.setMontoTotal(polizaGuardada.getPrimaVendidaTotal());
			facturaRepository.save(nuevaFactura);
		} catch (DataAccessException e) {
			throw new DataAccessException(e.getMessage()) {
            };
		}
	}
}
