package com.wajdigabsi.book.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                name = "wajdi",
                email = "conatct@wadji.com",
                 url = "https://wajdigabsi.com/tuto"
            ),
                description = "open api documenatation for spring secuirty",
                title = "OpenApi specification - wajdi",
                version = "1.0",
                termsOfService = "terms of service"

        ),
servers = {
                @Server(
                        description = "local ENV",
                        url = "http://localhost:8080/api/v1"
                ),
        @Server(
                description = "prod ENV",
                url = "https://wajdigabsituto.com/courses"
        )
},
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)


@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER


)
public class OpenApiConfig {
}
