package com.universales.gestionseguros.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.universales.gestionseguros.entity.Beneficiario;



@Repository("beneficiarioRepository")
public interface BeneficiarioRepository extends JpaRepository<Beneficiario, Integer> {

}
