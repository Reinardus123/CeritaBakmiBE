package com.CeritaBakmiBE.CB.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.stereotype.Component;

@Component
@OpenAPIDefinition (
        info = @Info(title = "API Documentation", version = "v1"),
        security = @SecurityRequirement(name= "bearerAuth"),
        servers = {
                @Server(
                        url = "https://cerita-bakmi-be.vercel.app/api"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class swaggerConfig {
}
