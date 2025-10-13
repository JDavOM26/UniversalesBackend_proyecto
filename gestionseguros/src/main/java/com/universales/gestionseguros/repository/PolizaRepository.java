package com.universales.gestionseguros.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.universales.gestionseguros.entity.Poliza;

@Repository("polizaRepository")
public interface PolizaRepository extends JpaRepository<Poliza, Integer> {
	List<Poliza> findByContratante(Integer idCliente);
    List<Poliza> findByContratanteIn(List<Integer> idClientes);
}
