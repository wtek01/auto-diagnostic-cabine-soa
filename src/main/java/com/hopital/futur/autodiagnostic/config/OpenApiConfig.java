package com.hopital.futur.autodiagnostic.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Auto-Diagnostic")
                        .version("1.0")
                        .description("API pour le système d'auto-diagnostic de l'hôpital du futur"));
    }
}