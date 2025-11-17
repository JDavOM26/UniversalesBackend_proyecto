package com.universales.gestionseguros.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.universales.entity.PaqueteCobertura;

@Repository
public interface PaqueteCoberturaRepository extends JpaRepository<PaqueteCobertura, Integer> {
	 List<PaqueteCobertura> findByIdPaquete(Integer idPaquete);
}
