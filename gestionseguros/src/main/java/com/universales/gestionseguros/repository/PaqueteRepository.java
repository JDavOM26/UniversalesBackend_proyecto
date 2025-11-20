package com.universales.gestionseguros.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.universales.entity.Paquete;

@Repository("paqueteRepository")
public interface PaqueteRepository extends JpaRepository<Paquete, Integer> {

}
