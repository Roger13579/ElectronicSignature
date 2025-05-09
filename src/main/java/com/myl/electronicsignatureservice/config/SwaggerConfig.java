package com.myl.electronicsignatureservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Electronic Signature Service")
                        .version("1.0")
                        .description("電子簽章 API")
                        .contact(new Contact()
                                .name("Roger")
                                .email("mingyao135@gmail.com")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("開發環境")
                ));
    }
}
