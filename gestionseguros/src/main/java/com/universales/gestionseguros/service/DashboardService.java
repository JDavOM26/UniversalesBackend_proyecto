package com.universales.gestionseguros.service;

import java.util.Collections;
import java.util.Map;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {
	NamedParameterJdbcTemplate npjt;

	public DashboardService(NamedParameterJdbcTemplate npjt) {
		this.npjt = npjt;
	}

	public Map<String, Object> buscarPrimasVendidasPolizasVendidas() {
		String query = """
				SELECT

				    NVL(SUM(CASE WHEN TRUNC(fecha_creacion) = TRUNC(SYSDATE)
				        THEN prima_vendida_total ELSE 0 END), 0) as primaVendidaDia,
				    COUNT(CASE WHEN TRUNC(fecha_creacion) = TRUNC(SYSDATE)
				        THEN 1 END) as polizasVendidasDia,


				    NVL(SUM(CASE WHEN TRUNC(fecha_creacion, 'MM') = TRUNC(SYSDATE, 'MM')
				        THEN prima_vendida_total ELSE 0 END), 0) as primaVendidaMes,
				    COUNT(CASE WHEN TRUNC(fecha_creacion, 'MM') = TRUNC(SYSDATE, 'MM')
				        THEN 1 END) as polizasVendidasMes
				FROM poliza
				""";

		return npjt.queryForMap(query, Collections.emptyMap());
	}

	public Map<String, Object> buscarCostosSiniestrosAtendidos() {
		String query = """
				SELECT

				    NVL(SUM(CASE WHEN estado_reclamo IN ('Aprobado')
				   AND TRUNC(fecha_decision_perito) = TRUNC(SYSDATE)
				        THEN NVL(monto_aprobado, 0) ELSE 0 END), 0) as costoSiniestrosDia,
				    COUNT(CASE WHEN estado_reclamo IN ('Aprobado', 'Rechazado')
				   AND estado_reclamo IN ('Aprobado', 'Rechazado')
				   AND TRUNC(fecha_decision_perito) = TRUNC(SYSDATE)
				        THEN 1 END) as siniestrosAtendidosDia,


				    NVL(SUM(CASE WHEN estado_reclamo IN ('Aprobado')
				   AND TRUNC(fecha_decision_perito, 'MM') = TRUNC(SYSDATE, 'MM')
				        THEN NVL(monto_aprobado, 0) ELSE 0 END), 0) as costoSiniestrosMes,
				    COUNT(CASE WHEN estado_reclamo IN ('Aprobado', 'Rechazado')
				   AND TRUNC(fecha_decision_perito, 'MM') = TRUNC(SYSDATE, 'MM')
				        THEN 1 END) as siniestrosAtendidosMes
				FROM reclamo
				""";

		return npjt.queryForMap(query, Collections.emptyMap());
	}

	public Map<String, Object> obtenerDashboardCompleto() {
		Map<String, Object> dashboard = buscarPrimasVendidasPolizasVendidas();
		dashboard.putAll(buscarCostosSiniestrosAtendidos());
		return dashboard;
	}

}
