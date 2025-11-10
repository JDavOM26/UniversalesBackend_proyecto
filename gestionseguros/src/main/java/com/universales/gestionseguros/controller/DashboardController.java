package com.universales.gestionseguros.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.universales.gestionseguros.service.DashboardService;

@RestController
@RequestMapping("/api/auth/dashboard")
public class DashboardController {

	private final DashboardService dashboardService;

	public DashboardController(DashboardService dashboardService) {
		this.dashboardService = dashboardService;
	}

	@GetMapping("/obtener-dashboard-general")
	public ResponseEntity<Map<String, Object>> getDashboard() {
		try {
		return ResponseEntity.ok(dashboardService.obtenerDashboardCompleto());
		}catch(Exception e) {
			return ResponseEntity.status(500).build();
		}
	}
}
