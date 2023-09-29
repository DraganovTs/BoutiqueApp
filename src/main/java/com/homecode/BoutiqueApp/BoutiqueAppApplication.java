package com.homecode.BoutiqueApp;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "My Boutique Application",
				version = "1.0.0",
				description = "small e-commerce app"
		)
)
public class BoutiqueAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoutiqueAppApplication.class, args);
	}

}
