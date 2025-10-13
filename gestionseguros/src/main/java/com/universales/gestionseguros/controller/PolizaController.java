package com.universales.gestionseguros.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.universales.gestionseguros.dto.PolizaDto;
import com.universales.gestionseguros.dto.PolizaResponseDto;
import com.universales.gestionseguros.entity.Cliente;
import com.universales.gestionseguros.entity.Poliza;
import com.universales.gestionseguros.repository.ClienteRepository;
import com.universales.gestionseguros.repository.PolizaRepository;
import com.universales.gestionseguros.service.EmitirPolizaService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

@RestController
@RequestMapping("/api/auth/polizas")
public class PolizaController {

	private final EmitirPolizaService polizaService;
	private final PolizaRepository polizaRepository;
	private final ClienteRepository clienteRepository;
	private final ModelMapper modelMapper;

	public PolizaController(EmitirPolizaService polizaService,ClienteRepository clienteRepository, PolizaRepository polizaRepository, ModelMapper modelMapper) {
		this.polizaService = polizaService;
		this.polizaRepository = polizaRepository;
		this.clienteRepository = clienteRepository;
		this.modelMapper = modelMapper;
	}
	
	
	 @GetMapping("/buscar-poliza")
	    public ResponseEntity<List<PolizaResponseDto>> encontrarClientePorDocumento(
	            @RequestParam String valor,
	            @RequestParam String tipoValor) {
	        try {
	            List<Poliza> polizas;
	            if (tipoValor.equals("idPoliza")) {
	                Integer idPoliza = Integer.parseInt(valor);
	                Poliza poliza = polizaRepository.findById(idPoliza)
	                        .orElseThrow(() -> new RuntimeException("Póliza no encontrada"));
	                polizas = Collections.singletonList(poliza);
	            }  else if (tipoValor.equals("nombreApellido")) {
	                String[] partes = valor.split(" ");
	                if (partes.length < 2) {
	                    throw new IllegalArgumentException("Debe proporcionar al menos un nombre y un apellido");
	                }
	                String nombre = partes[0];
	                String apellido = partes[1];
	                List<Cliente> clientes = clienteRepository.findByPrimerNombreAndApellidoPrimero(nombre, apellido);
	                if (clientes.isEmpty()) {
	                    throw new RuntimeException("No se encontraron clientes con el nombre y apellido proporcionados");
	                }
	                polizas = polizaRepository.findByContratanteIn(
	                        clientes.stream().map(Cliente::getIdCliente).collect(Collectors.toList()));
	                if (polizas.isEmpty()) {
	                    throw new RuntimeException("No se encontraron pólizas para los clientes con el nombre y apellido proporcionados");
	                }
	            } else if (tipoValor.equals("dpi")) {
	                Cliente cliente = clienteRepository.findByDpi(valor);
	                polizas = polizaRepository.findByContratante(cliente.getIdCliente());
	                if (polizas.isEmpty()) {
	                    throw new RuntimeException("No se encontraron pólizas para el cliente con el DPI proporcionado");
	                }
	            } else if (tipoValor.equals("nit")) {
	                Cliente cliente = clienteRepository.findByNit(valor);
	                polizas = polizaRepository.findByContratante(cliente.getIdCliente());
	                if (polizas.isEmpty()) {
	                    throw new RuntimeException("No se encontraron pólizas para el cliente con el NIT proporcionado");
	                }
	            } else {
	                throw new IllegalArgumentException("Tipo de búsqueda no válido: " + tipoValor);
	            }

	            List<PolizaResponseDto> response = polizas.stream().map(poliza -> {
	                PolizaResponseDto dto = modelMapper.map(poliza, PolizaResponseDto.class);
	                Cliente cliente = clienteRepository.findById(poliza.getContratante())
	                        .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
	                dto.setNombreContratante(cliente.getPrimerNombre() + " " + cliente.getApellidoPrimero());
	                return dto;
	            }).collect(Collectors.toList());

	            return ResponseEntity.ok(response);
	        } catch (NumberFormatException e) {
	            return ResponseEntity.badRequest().body(Collections.emptyList());
	        } catch (IllegalArgumentException e) {
	            return ResponseEntity.badRequest().body(Collections.emptyList());
	        } catch (RuntimeException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
	        }
	    }

	@PostMapping("/emitir-poliza")
    public ResponseEntity<String> emitirPoliza(@RequestBody PolizaDto polizaDto) {
        try {
            polizaService.emitirPoliza(polizaDto);
            
            return new ResponseEntity<>( "Póliza creada exitosamente",HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Error al crear la póliza: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error inesperado al crear la póliza: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}