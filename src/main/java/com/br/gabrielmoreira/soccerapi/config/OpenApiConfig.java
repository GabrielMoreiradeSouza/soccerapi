package com.br.gabrielmoreira.soccerapi.config;

import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI bootCampOpenAPI() {
        return new OpenAPI()
                .info( new Info()
                        .title("Soccer API 2026")
                        .version("1.0")
                        .description("Documentação Ofical API"));
    }
}
