package fit.se.kltn.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(name = "Bùi Xuân Mạnh", email = "bmanh7920@gmail.com"),
                description = "API cho website, mobile app OKLIB",
                title = "API OKLIB",
                version = "v1.0.0"
        )
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT authentication ",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
