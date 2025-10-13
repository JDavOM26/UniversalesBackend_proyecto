package com.universales.gestionseguros.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.universales.gestionseguros.entity.Reclamo;

@Repository("reclamoRepository")
public interface ReclamoRepository extends JpaRepository<Reclamo, Integer> {
	List<Reclamo> findByAjustador(Integer ajustador);
	List<Reclamo> findByEstadoReclamo(String estadoReclamo);
}
