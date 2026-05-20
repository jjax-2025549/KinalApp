package com.julianjax.kinalapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity  // Habilita @PreAuthorize en los controllers
public class KinalAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(KinalAppApplication.class, args);
	}
}
