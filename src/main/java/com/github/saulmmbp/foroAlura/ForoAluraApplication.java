package com.github.saulmmbp.foroAlura;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.*;
import io.swagger.v3.oas.annotations.security.*;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Foro Alura API", description = "Practice project - Alura Challenge ONE", 
			contact = @Contact(name = "Saul Malagon Martinez", email = "smalagonmtz@gmail.com"),
			version = "1.0.0"))
@SecurityScheme(name = "Foro_Alura_Auth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
public class ForoAluraApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForoAluraApplication.class, args);
	}

}
