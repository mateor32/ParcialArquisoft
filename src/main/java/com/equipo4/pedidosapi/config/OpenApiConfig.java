package com.equipo4.pedidosapi.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .servers(List.of(new Server().url("/api").description("Servidor relativo (mismo origen)")))
                .info(new Info()
                        .title("API de Gestión de Pedidos - Equipo 4")
                        .description("API REST para la gestión integral de pedidos.")
                        .version("1.0.0")
                        .contact(new Contact().name("Equipo 4").email("equipo4@example.com"))
                        .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0.html")));
    }

}
