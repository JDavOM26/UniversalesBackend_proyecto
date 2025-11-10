package com.universales.gestionseguros.service;

import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import com.universales.gestionseguros.dto.PageResultPoliza;

@Service
public class BuscarPolizaService {

	NamedParameterJdbcTemplate npjt;

	public BuscarPolizaService(NamedParameterJdbcTemplate npjt) {
		this.npjt = npjt;

	}

	public PageResultPoliza<Map<String, Object>> buscarCoberturasPoliza(String busqueda, int page, int size) {

		if (page < 0)
			page = 0;
		if (size < 1)
			size = 10;

		int offset = page * size;

		if (busqueda == null || busqueda.trim().isEmpty()) {
			busqueda = "";
		}

		String countQuery = """
				SELECT
				  COUNT(*)
				      FROM poliza p
				      INNER JOIN cliente c ON p.contratante = c.id_cliente
				      WHERE
				          UPPER(
				              REGEXP_REPLACE(
				                  TRIM(c.primer_nombre || ' ' ||
				                       NVL(c.segundo_nombre, '') || ' ' ||
				                       NVL(c.tercer_nombre, '') || ' ' ||
				                       c.apellido_primero || ' ' ||
				                       NVL(c.apellido_segundo, '') || ' ' ||
				                       NVL(c.apellido_tercero, '')),
				                  ' +', ' '
				              )
				          ) LIKE UPPER('%' || :busqueda || '%')
				          OR UPPER(c.dpi) LIKE UPPER('%' || :busqueda || '%')
				          OR UPPER(c.nit) LIKE UPPER('%' || :busqueda || '%')
				          OR TO_CHAR(p.id_poliza) LIKE '%' || :busqueda || '%'
				      """;

		String dataQuery = """
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
				    UPPER(
				        REGEXP_REPLACE(
				            TRIM(c.primer_nombre || ' ' ||
				                 NVL(c.segundo_nombre, '') || ' ' ||
				                 NVL(c.tercer_nombre, '') || ' ' ||
				                 c.apellido_primero || ' ' ||
				                 NVL(c.apellido_segundo, '') || ' ' ||
				                 NVL(c.apellido_tercero, '')),
				            ' +', ' '
				        )
				    ) LIKE UPPER('%' || :busqueda || '%')
				    OR UPPER(c.dpi) LIKE UPPER('%' || :busqueda || '%')
				    OR UPPER(c.nit) LIKE UPPER('%' || :busqueda || '%')
				    OR TO_CHAR(p.id_poliza) LIKE '%' || :busqueda || '%'
				ORDER BY p.fecha_creacion DESC
				OFFSET :offset ROWS FETCH NEXT :size ROWS ONLY
				""";

		SqlParameterSource params = new MapSqlParameterSource().addValue("busqueda", busqueda)
				.addValue("offset", offset).addValue("size", size);

		Long count = npjt.queryForObject(countQuery, params, Long.class);
		if (count == null) {
		    throw new IllegalStateException("El conteo no puede ser nulo");
		}
		long totalElements = count;

		List<Map<String, Object>> content = npjt.queryForList(dataQuery, params);

		return new PageResultPoliza<>(content, totalElements, page, size);
	}
}