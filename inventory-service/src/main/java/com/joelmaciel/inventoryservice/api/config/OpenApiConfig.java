package com.joelmaciel.inventoryservice.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("INVENTORY SERVICE API")
                        .description("Developed by Joel Maciel")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Joel Maciel")
                                .url("https://www.linkedin.com/in/vianamaciel-java-backend/")
                                .email("jmviana37@example.com"))
                );

    }
}