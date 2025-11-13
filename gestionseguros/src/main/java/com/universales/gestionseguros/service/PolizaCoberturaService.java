package com.universales.gestionseguros.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import com.universales.gestionseguros.repository.PolizaCoberturaRepository;

@Service
public class PolizaCoberturaService {
	
	private final PolizaCoberturaRepository polizaCoberturaRepository;
	
	NamedParameterJdbcTemplate npjt;
	
	public PolizaCoberturaService (NamedParameterJdbcTemplate npjt, PolizaCoberturaRepository polizaCoberturaRepository) {
		this.npjt = npjt;
		this.polizaCoberturaRepository = polizaCoberturaRepository;
	}
	public List<Map<String, Object>> buscarCoberturasPoliza(Integer idPoliza) {
        String query = """
                SELECT
                    c.id_cobertura,
                    c.nombre,
                    pc.suma_asegurada_disponible
                FROM COBERTURA c
                INNER JOIN POLIZA_COBERTURA pc ON c.id_cobertura = pc.id_cobertura
                INNER JOIN POLIZA p ON pc.id_poliza = p.id_poliza
                WHERE p.id_poliza = :idPoliza
                """;

        SqlParameterSource nameParameter = new MapSqlParameterSource().addValue("idPoliza", idPoliza);
        return npjt.queryForList(query, nameParameter);
    }
	
	public BigDecimal getSumaAseguradaDisponible(Integer idPoliza, Integer idCobertura) {
        BigDecimal sumaAsegurada = polizaCoberturaRepository.findSumaAseguradaDisponibleByIdPolizaAndIdCobertura(idPoliza, idCobertura);
        if (sumaAsegurada == null) {
            throw new DataAccessException("PolizaCobertura no encontrada por ids: " + idPoliza + "& " + idCobertura) {
            };
        }
        return sumaAsegurada;
    }
}
