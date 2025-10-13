package com.universales.gestionseguros.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.universales.gestionseguros.entity.PolizaCobertura;

@Repository("polizaCoberturaRepository")
public interface PolizaCoberturaRepository extends JpaRepository<PolizaCobertura, Integer> {
    PolizaCobertura findByIdCoberturaAndIdPoliza (Integer idCobertura, Integer idPoliza);
}
