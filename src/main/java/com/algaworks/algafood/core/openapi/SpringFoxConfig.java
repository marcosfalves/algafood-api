package com.algaworks.algafood.core.openapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig {

    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api"))
                //.paths(PathSelectors.ant("/restaurantes/*"))
                .build()
                .useDefaultResponseMessages(false)
                .globalResponses(HttpMethod.GET, globalGetResponseMessages())
                .globalResponses(HttpMethod.POST, globalPostResponseMessages())
                .globalResponses(HttpMethod.PUT, globalPutResponseMessages())
                .globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
                .apiInfo(apiInfo())
                .tags(new Tag("Cidades", "Gerencia as Cidades"));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("AlgaFood API")
                .description("API aberta para clientes e restaurantes")
                .version("1")
                .contact(new Contact("Marcos F. Alves", "https://github.com/marcosfalves", "marcosf_alves@outlook.com"))
                .build();
    }

    private List<Response> globalGetResponseMessages() {
        return buildResponseMessagesHttpStatusAccepted(
                Arrays.asList(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        HttpStatus.NOT_ACCEPTABLE
                ));
    }

    private List<Response> globalPostResponseMessages() {
        return buildResponseMessagesHttpStatusAccepted(
                Arrays.asList(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        HttpStatus.BAD_REQUEST,
                        HttpStatus.NOT_ACCEPTABLE,
                        HttpStatus.UNSUPPORTED_MEDIA_TYPE
                ));
    }

    private List<Response> globalPutResponseMessages() {
        return buildResponseMessagesHttpStatusAccepted(
                Arrays.asList(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        HttpStatus.BAD_REQUEST,
                        HttpStatus.NOT_ACCEPTABLE,
                        HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                        HttpStatus.NOT_FOUND
                ));
    }

    private List<Response> globalDeleteResponseMessages() {
        return buildResponseMessagesHttpStatusAccepted(
                Arrays.asList(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        HttpStatus.BAD_REQUEST,
                        HttpStatus.NOT_FOUND
                ));
    }

    private List<Response> buildResponseMessagesHttpStatusAccepted(List<HttpStatus> httpStatusAccepted) {
        Map<HttpStatus, String> messages = new HashMap<>();
        messages.put(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor");
        messages.put(HttpStatus.BAD_REQUEST, "Requisição inválida (erro do cliente)");
        messages.put(HttpStatus.NOT_FOUND, "Recurso não encontrado");
        messages.put(HttpStatus.NOT_ACCEPTABLE, "Recurso não possui representação que poderia ser aceita pelo consumidor");
        messages.put(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Requisição recusada porque o corpo está em um formato não suportado");

        return httpStatusAccepted.stream()
                .map(httpStatus -> new ResponseBuilder()
                        .code(String.valueOf(httpStatus.value()))
                        .description(messages.get(httpStatus))
                        .build())
                .collect(Collectors.toList());
    }
}
