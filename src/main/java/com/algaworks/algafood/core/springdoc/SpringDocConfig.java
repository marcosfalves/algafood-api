package com.algaworks.algafood.core.springdoc;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
@SecurityScheme(name = "security_auth", type = SecuritySchemeType.OAUTH2,
        flows = @OAuthFlows(authorizationCode = @OAuthFlow(
                authorizationUrl = "${springdoc.algafood.oauth-flow.authorization-url}",
                tokenUrl = "${springdoc.algafood.oauth-flow.token-url}",
                scopes = {
                        @OAuthScope(name = "READ", description = "read scope"),
                        @OAuthScope(name = "WRITE", description = "write scope")
                }
            )
        )
)
public class SpringDocConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(buildAppInfo())
                .tags(buildTags());
    }

    private Info buildAppInfo() {
        var licence = new License()
                .name("Apache 2.0")
                .url("http://springdoc.com");

        var contact = new Contact()
                .name("Marcos F. Alves")
                .email("marcosf_alves@outlook.com")
                .url("https://github.com/marcosfalves");

        return new Info()
                .title("AlgaFood API")
                .version("v1")
                .description("REST API aberta para clientes e restaurantes")
                .license(licence)
                .contact(contact);
    }

    private List<Tag> buildTags() {
        return Arrays.asList(
          new Tag().name("Cidades").description("Gerencia as cidades")
        );
    }
}
