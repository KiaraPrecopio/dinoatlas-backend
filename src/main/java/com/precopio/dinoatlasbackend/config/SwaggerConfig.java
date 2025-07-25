package com.precopio.dinoatlasbackend.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("DinoAtlas API")
                        .version("1.0.0")
                        .description("API documentation for the DinoAtlas application")
                .contact(new io.swagger.v3.oas.models.info.Contact()
                        .name("DinoAtlas Support")
                        .url("https://www.dinoatlas.com/support")
                        .email("kprecopio@gmail.com")));
    }
}
