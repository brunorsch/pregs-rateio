package br.app.pregsrateio.common.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "PregsRateio",
        version = "v1",
        description = "API para integração com frontend do PregsRateio",
        contact = @Contact(
            name = "PregsRateio",
            email = "suporte@pregsrateio.app.br",
            url = "https://github.com/brunorsch/pregs-rateio"
        )
    )
)
@SecurityScheme(
    name = "jwt",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT"
)
public class SwaggerConfig {

}
