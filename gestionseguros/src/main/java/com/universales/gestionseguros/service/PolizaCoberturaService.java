package com.universales.gestionseguros.service;

import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

@Service
public class PolizaCoberturaService {
	
	NamedParameterJdbcTemplate npjt;
	
	public PolizaCoberturaService (NamedParameterJdbcTemplate npjt) {
		this.npjt = npjt;
	}

	public List<Map<String, Object>> buscarCoberturasPoliza(Integer idPoliza) {
		String query = """
				SELECT c.id_cobertura, c.nombre, pc.suma_asegurada_disponible
				FROM COBERTURA c
				INNER JOIN POLIZA_COBERTURA pc ON c.id_cobertura = pc.id_cobertura
				INNER JOIN POLIZA p ON pc.id_poliza = p.id_poliza
				WHERE p.id_poliza = :idPoliza
								""";

		SqlParameterSource nameParameter = new MapSqlParameterSource().addValue("idPoliza", idPoliza);
		return npjt.queryForList(query, nameParameter);
	}
}
