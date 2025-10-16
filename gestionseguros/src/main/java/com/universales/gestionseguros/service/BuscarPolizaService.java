package com.universales.gestionseguros.service;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

@Service
public class BuscarPolizaService {

	NamedParameterJdbcTemplate npjt;

	public BuscarPolizaService(NamedParameterJdbcTemplate npjt) {
		this.npjt = npjt;
	}

	public List<Map<String, Object>> buscarCoberturasPoliza(String busqueda) {
		String query = """
				    SELECT
				      p.id_poliza AS "idPoliza",
                    p.nombre_poliza AS "nombrePoliza",
                    p.estado AS "estado",
                    p.prima_vendida_total AS "primaVendidaTotal",
                    TO_CHAR(p.fecha_creacion, 'YYYY-MM-DD') AS "fechaCreacion",
                    TO_CHAR(p.fecha_vencimiento, 'YYYY-MM-DD') AS "fechaVencimiento",
                    p.contratante AS "contratante",
                    p.vendedor AS "vendedor",
                    p.id_paquete AS "idPaquete",
                    TRIM(c.primer_nombre || ' ' ||
                         NVL(c.segundo_nombre, '') || ' ' ||
                         NVL(c.tercer_nombre, '') || ' ' ||
                         c.apellido_primero || ' ' ||
                         NVL(c.apellido_segundo, '') || ' ' ||
                         NVL(c.apellido_tercero, '')) AS "nombreContratante"
				FROM poliza p
				INNER JOIN cliente c ON p.contratante = c.id_cliente
				WHERE 
    UPPER(TRIM(c.primer_nombre || ' ' || 
               NVL(c.segundo_nombre, '') || ' ' || 
               NVL(c.tercer_nombre, '') || ' ' || 
               c.apellido_primero || ' ' || 
               NVL(c.apellido_segundo, '') || ' ' || 
               NVL(c.apellido_tercero, ''))) LIKE UPPER('%' || :busqueda || '%')
    OR UPPER(c.dpi) LIKE UPPER('%' || :busqueda || '%')
    OR UPPER(c.nit) LIKE UPPER('%' || :busqueda || '%')
    OR TO_CHAR(p.id_poliza) LIKE '%' || :busqueda || '%'
ORDER BY p.fecha_creacion DESC
	""";

		SqlParameterSource nameParameter = new MapSqlParameterSource().addValue("busqueda", busqueda);
		return npjt.queryForList(query, nameParameter);
	}

}
