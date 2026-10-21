package com.udea.labdrools;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LabdroolsApplication {

	public static void main(String[] args) {
		// Deshabilita la compilación JIT de MVEL antes de cargar Spring Boot
		System.setProperty("mvel2.disable.jit", "true");

		SpringApplication.run(LabdroolsApplication.class, args);
	}
}