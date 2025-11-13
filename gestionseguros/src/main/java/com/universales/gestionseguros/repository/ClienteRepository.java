package com.universales.gestionseguros.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.universales.gestionseguros.entity.Cliente;


@Repository("clienteRepository")
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	Cliente findByNit(String nit);
    Cliente findByDpi(String dpi);
 
 
    
    @Query("""
            SELECT DISTINCT c FROM Cliente c
            WHERE UPPER(c.dpi) LIKE CONCAT('%', UPPER(:busqueda), '%')
            OR UPPER(c.nit) LIKE CONCAT('%', UPPER(:busqueda), '%')
            """)
    	Cliente buscarPorDpiONit(@Param("busqueda") String busqueda);

}
