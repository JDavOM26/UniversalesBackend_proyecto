package com.universales.gestionseguros.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.universales.gestionseguros.entity.Cliente;


@Repository("clienteRepository")
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	Cliente findByNit(String nit);
    Cliente findByDpi(String dpi);
 
    List<Cliente> findByPrimerNombreAndApellidoPrimero(String primerNombre, String apellidoPrimero);

}
