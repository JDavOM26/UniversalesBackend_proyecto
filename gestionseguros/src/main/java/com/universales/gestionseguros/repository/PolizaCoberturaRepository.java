package com.universales.gestionseguros.repository;



import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.universales.gestionseguros.entity.PolizaCobertura;

@Repository("polizaCoberturaRepository")
public interface PolizaCoberturaRepository extends JpaRepository<PolizaCobertura, Integer> {
    PolizaCobertura findByIdCoberturaAndIdPoliza (Integer idCobertura, Integer idPoliza);
    
    @Query("SELECT pc.sumaAseguradaDisponible FROM PolizaCobertura pc WHERE pc.idPoliza = :idPoliza AND pc.idCobertura = :idCobertura")
    BigDecimal findSumaAseguradaDisponibleByIdPolizaAndIdCobertura(@Param("idPoliza") Integer idPoliza, @Param("idCobertura") Integer idCobertura);

}
