package com.universales.gestionseguros;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.universales.entity")
public class GestionDeSegurosApplication {
    
	public static void main(String[] args) {
		SpringApplication.run(GestionDeSegurosApplication.class, args);
	}

}
