package io.github.apedrina.web.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "ArcOne-Web API",
                description = "Learning Management System"
        )
)
class OpenApiConfig {

}
