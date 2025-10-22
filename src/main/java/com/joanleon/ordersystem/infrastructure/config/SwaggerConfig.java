package com.joanleon.ordersystem.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    OpenAPI orderSystemOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Order System API")
                        .description("API REST para la gestión de pedidos y clientes")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Joan Leon")
                                .email("contacto@joanleon.com")
                                .url("https://github.com/joanleon"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8090")
                                .description("Servidor de Desarrollo"),
                        new Server()
                                .url("https://order-system-hdns.onrender.com")
                                .description("Servidor de Producción")))
                .tags(List.of(
                        new Tag()
                                .name("Clientes")
                                .description("Operaciones relacionadas con clientes"),
                        new Tag()
                                .name("Pedidos")
                                .description("Operaciones relacionadas con pedidos")));
    }
}