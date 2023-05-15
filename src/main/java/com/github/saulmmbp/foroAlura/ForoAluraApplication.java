package com.github.saulmmbp.foroAlura;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.*;
import io.swagger.v3.oas.annotations.security.*;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Foro Alura API", description = "Practice project - Alura Challenge ONE\n"
		+ "para más información visitar el repositorio [Foro Alura](https://github.com/SaulMMBP/foro-alura)", 
			contact = @Contact(name = "Saul Malagon Martinez", email = "smalagonmtz@gmail.com"),
			version = "1.0.0"))
@SecurityScheme(name = "Foro Alura Auth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT",
	description = "Ingresa el JWT Token. Este puede ser obtenido desde /login. Para ejecutar pruebas utilizando la base"
			+ "de datos que se provee en el repositorio [Foro Alura](https://github.com/SaulMMBP/foro-alura), "
			+ "puedes utilizar: \n"
			+ "- Rol `ADMIN`\n"
			+ "```\n{\n"
			+ "\t'usuario':'saul@mail.com',\n"
			+ "\t'contrasena':'1234'\n"
			+ "}\n```\n"
			+ "- Rol `INSTRUCTOR`\n"
			+ "```\n{\n"
			+ "\t'usuario':'daniel@mail.com',\n"
			+ "\t'contrasena':'1234'\n"
			+ "}\n```\n"
			+ "- Rol `USER`\n"
			+ "```\n{\n"
			+ "\t'usuario':'will@mail.com',\n"
			+ "\t'contrasena':'1234'\n"
			+ "}\n```\n")
public class ForoAluraApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForoAluraApplication.class, args);
	}

}
