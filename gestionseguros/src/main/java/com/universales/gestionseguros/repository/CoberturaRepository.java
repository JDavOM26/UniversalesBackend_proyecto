package com.universales.gestionseguros.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.universales.entity.Cobertura;

@Repository("coberturaRepository")
public interface CoberturaRepository extends JpaRepository<Cobertura, Integer> {

}
