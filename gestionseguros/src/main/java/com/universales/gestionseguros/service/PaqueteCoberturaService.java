package com.universales.gestionseguros.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.universales.gestionseguros.entity.PaqueteCobertura;
import com.universales.gestionseguros.repository.PaqueteCoberturaRepository;

@Service	
public class PaqueteCoberturaService {

	private final PaqueteCoberturaRepository paqueteCoberturaRepository;
	
	public PaqueteCoberturaService(PaqueteCoberturaRepository paqueteCoberturaRepository) {
		this.paqueteCoberturaRepository = paqueteCoberturaRepository;
	}
	
    public void crearPaqueteCobertura(List<Integer> idCoberturas, Integer idPaquete) {
        List<PaqueteCobertura> lista = idCoberturas.stream().map(idCobertura -> {
            PaqueteCobertura pc = new PaqueteCobertura();
            pc.setIdPaquete(idPaquete);
            pc.setIdCobertura(idCobertura);
            return pc;
        }).toList(); 

        paqueteCoberturaRepository.saveAll(lista);
    }
}