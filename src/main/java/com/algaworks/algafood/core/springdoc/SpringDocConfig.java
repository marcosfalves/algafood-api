package com.algaworks.algafood.core.springdoc;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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

    private static final String badRequestResponse = "BadRequestResponse";
    private static final String badRequestInvalidParamResponse = "BadRequestInvalidParamResponse";
    private static final String notFoundResponse = "NotFoundResponse";
    private static final String unsupportedMediaTypeResponse = "UnsupportedMediaTypeResponse";
    private static final String internalServerErrorResponse = "InternalServerErrorResponse";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(buildAppInfo())
                .tags(buildTags())
                .components(new Components()
                        .schemas(buildSchemas())
                        .responses(buildGlobalApiResponses())
                );
    }

    @Bean
    public OpenApiCustomiser openApiCustomiser() {
        return openApi -> openApi.getPaths()
                .values()
                .forEach(pathItem -> pathItem.readOperationsMap()
                        .forEach(((httpMethod, operation) -> {
                            var responses = operation.getResponses();

                            var hasRequestBody = operation.getRequestBody() != null;
                            var hasResponseBody = responses.get("204") == null;

                            switch (httpMethod){
                                case GET -> globalGetResponseMessages(responses);
                                case POST -> globalPostResponseMessages(responses);
                                case PUT, PATCH  -> globalPutResponseMessages(responses, hasRequestBody, hasResponseBody);
                                case DELETE -> globalDeleteResponseMessages(responses);
                            }

                        }))
                );
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
          new Tag().name("Cidades").description("Gerencia as cidades"),
          new Tag().name("Grupos").description("Gerencia os grupos de usuário"),
          new Tag().name("Cozinhas").description("Gerencia as cozinhas"),
          new Tag().name("Formas de Pagamento").description("Gerencia as formas de pagamento"),
          new Tag().name("Pedidos").description("Gerencia os pedidos"),
          new Tag().name("Restaurantes").description("Gerencia os restaurantes"),
          new Tag().name("Estados").description("Gerencia os estados"),
          new Tag().name("Produtos").description("Gerencia os produtos dos restaurantes")
        );
    }

    private Map<String, Schema> buildSchemas() {
        final Map<String, Schema> schemaMap = new HashMap<>();

        var problemSchema = ModelConverters.getInstance().read(Problem.class);
        var problemObjectSchema = ModelConverters.getInstance().read(Problem.Object.class);

        schemaMap.putAll(problemSchema);
        schemaMap.putAll(problemObjectSchema);

        return schemaMap;
    }

    private Map<String, ApiResponse> buildGlobalApiResponses() {
        final Map<String, ApiResponse> apiResponseMap = new HashMap<>();

        var content = new Content()
                .addMediaType(APPLICATION_JSON_VALUE,
                        new MediaType().schema(new Schema<Problem>().$ref("Problema")));

        apiResponseMap.put(badRequestResponse, new ApiResponse()
                .description("Requisição inválida (erro do cliente)")
                .content(content));

        apiResponseMap.put(badRequestInvalidParamResponse, new ApiResponse()
                .description("Parâmetro informado possui formato inválido")
                .content(content));

        apiResponseMap.put(notFoundResponse, new ApiResponse()
                .description("Recurso não encontrado")
                .content(content));

        apiResponseMap.put(unsupportedMediaTypeResponse, new ApiResponse()
                .description("Requisição recusada porque o corpo está em um formato não suportado")
                .content(content));

        apiResponseMap.put(internalServerErrorResponse, new ApiResponse()
                .description("Erro interno no servidor")
                .content(content));

        return apiResponseMap;
    }

    private void globalGetResponseMessages(ApiResponses responses) {
        //Utilizado direto nos controllers para forçar carregar o Schema de Problem.class
        //responses.addApiResponse("400", new ApiResponse().$ref(badRequestInvalidParamResponse));

        buildResponseMessagesHttpStatusAccepted(
                responses,
                Arrays.asList(
                        HttpStatus.NOT_ACCEPTABLE,
                        HttpStatus.INTERNAL_SERVER_ERROR
                ));
    }

    private void globalPostResponseMessages(ApiResponses responses) {
        buildResponseMessagesHttpStatusAccepted(
                responses,
                Arrays.asList(
                        HttpStatus.BAD_REQUEST,
                        HttpStatus.NOT_ACCEPTABLE,
                        HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                        HttpStatus.INTERNAL_SERVER_ERROR
                ));
    }

    private void globalPutResponseMessages(ApiResponses responses, boolean hasRequestBody, boolean hasResponseBody) {
        buildResponseMessagesHttpStatusAccepted(
                responses,
                Arrays.asList(
                        HttpStatus.BAD_REQUEST,
                        HttpStatus.NOT_FOUND,
                        HttpStatus.INTERNAL_SERVER_ERROR
                ));

        if (hasRequestBody) {
            buildResponseMessagesHttpStatusAccepted(
                    responses,
                    Arrays.asList(
                            HttpStatus.UNSUPPORTED_MEDIA_TYPE
                    ));
        }

        if (hasResponseBody) {
            buildResponseMessagesHttpStatusAccepted(
                    responses,
                    Arrays.asList(
                            HttpStatus.NOT_ACCEPTABLE
                    ));
        }
    }

    private void globalDeleteResponseMessages(ApiResponses responses) {
        buildResponseMessagesHttpStatusAccepted(
                responses,
                Arrays.asList(
                        HttpStatus.BAD_REQUEST,
                        HttpStatus.NOT_FOUND,
                        HttpStatus.INTERNAL_SERVER_ERROR
                ));
    }

    private void buildResponseMessagesHttpStatusAccepted(ApiResponses responses, List<HttpStatus> httpStatusAccepted) {
        Map<HttpStatus, ApiResponse> messages = new HashMap<>();
        messages.put(HttpStatus.INTERNAL_SERVER_ERROR, new ApiResponse().$ref(internalServerErrorResponse));
        messages.put(HttpStatus.BAD_REQUEST, new ApiResponse().$ref(badRequestResponse));
        messages.put(HttpStatus.NOT_FOUND, new ApiResponse().$ref(notFoundResponse));
        messages.put(HttpStatus.NOT_ACCEPTABLE, new ApiResponse().description("Recurso não possui representação que poderia ser aceita pelo consumidor"));
        messages.put(HttpStatus.UNSUPPORTED_MEDIA_TYPE, new ApiResponse().$ref(unsupportedMediaTypeResponse));

        httpStatusAccepted.forEach(httpStatus ->
                responses.addApiResponse(String.valueOf(httpStatus.value()), messages.get(httpStatus))
        );
    }
}
