package com.fiap.soa.booking_room.config;

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
                        .title("API de Reserva de Hotel - FIAP")
                        .version("1.0")
                        .description("Sistema de gerenciamento de reservas, check-in e check-out (CP2)."));
    }
}