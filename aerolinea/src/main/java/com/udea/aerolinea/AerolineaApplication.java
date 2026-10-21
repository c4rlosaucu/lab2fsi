package com.udea.aerolinea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AerolineaApplication {

	public static void main(String[] args) {
		// Igual que en labdrools: se deshabilita el JIT de MVEL antes de arrancar Spring Boot
		System.setProperty("mvel2.disable.jit", "true");
		SpringApplication.run(AerolineaApplication.class, args);
	}
}
