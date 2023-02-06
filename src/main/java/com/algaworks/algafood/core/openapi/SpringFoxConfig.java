package com.algaworks.algafood.core.openapi;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.CozinhaModel;
import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.api.openapi.model.PageableModelOpenApi;
import com.algaworks.algafood.api.openapi.model.PagedModelOpenApi;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.ServletWebRequest;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RepresentationBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.json.JacksonModuleRegistrar;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig {

    private TypeResolver typeResolver = new TypeResolver();

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
                .additionalModels(typeResolver.resolve(Problem.class))
                .ignoredParameterTypes(ServletWebRequest.class)
                .directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
                .alternateTypeRules(
                        buildPageTypeRole(CozinhaModel.class),
                        buildPageTypeRole(PedidoResumoModel.class))
                .apiInfo(apiInfo())
                .tags(new Tag("Cidades", "Gerencia as Cidades"),
                        new Tag("Grupos", "Gerencia os grupos de usuários"),
                        new Tag("Cozinhas", "Gerencia as cozinhas"),
                        new Tag("Formas de Pagamento", "Gerencia as formas de pagamento"));
    }

    @Bean
    public JacksonModuleRegistrar springFoxJacksonConfig() {
        return objectMapper -> objectMapper.registerModule(new JavaTimeModule());
    }

    private <T> AlternateTypeRule buildPageTypeRole(Class<T> classModel) {
        return AlternateTypeRules.newRule(
                typeResolver.resolve(Page.class, classModel),
                typeResolver.resolve(PagedModelOpenApi.class, classModel)
        );
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

    private Consumer<RepresentationBuilder> getProblemModelReference() {
        return r -> r.model(m -> m.name("Problema")
                .referenceModel(ref -> ref.key(k -> k.qualifiedModelName(
                        q -> q.name("Problema").namespace("com.algaworks.algafood.api.exceptionhandler")))));
    }

    private List<Response> buildResponseMessagesHttpStatusAccepted(List<HttpStatus> httpStatusAccepted) {
        Map<HttpStatus, String> messages = new HashMap<>();
        messages.put(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor");
        messages.put(HttpStatus.BAD_REQUEST, "Requisição inválida (erro do cliente)");
        messages.put(HttpStatus.NOT_FOUND, "Recurso não encontrado");
        messages.put(HttpStatus.NOT_ACCEPTABLE, "Recurso não possui representação que poderia ser aceita pelo consumidor");
        messages.put(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Requisição recusada porque o corpo está em um formato não suportado");

        List<HttpStatus> httpStatusProblemResponse = Arrays.asList(
                HttpStatus.INTERNAL_SERVER_ERROR,
                HttpStatus.BAD_REQUEST,
                HttpStatus.NOT_FOUND,
                HttpStatus.UNSUPPORTED_MEDIA_TYPE
        );

        return httpStatusAccepted.stream()
                .map(httpStatus -> {
                    if (httpStatusProblemResponse.contains(httpStatus)) {
                        return new ResponseBuilder()
                                .code(String.valueOf(httpStatus.value()))
                                .description(messages.get(httpStatus))
                                .representation(MediaType.APPLICATION_JSON)
                                .apply(getProblemModelReference())
                                .build();
                    } else {
                        return new ResponseBuilder()
                                .code(String.valueOf(httpStatus.value()))
                                .description(messages.get(httpStatus))
                                .build();
                    }
                })
                .collect(Collectors.toList());
    }
}
