package com.algaworks.algafood.core.springfox;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.CidadeModel;
import com.algaworks.algafood.api.v1.model.CozinhaModel;
import com.algaworks.algafood.api.v1.model.EstadoModel;
import com.algaworks.algafood.api.v1.model.FormaPagamentoModel;
import com.algaworks.algafood.api.v1.model.GrupoModel;
import com.algaworks.algafood.api.v1.model.PedidoResumoModel;
import com.algaworks.algafood.api.v1.model.PermissaoModel;
import com.algaworks.algafood.api.v1.model.ProdutoModel;
import com.algaworks.algafood.api.v1.model.RestauranteBasicoModel;
import com.algaworks.algafood.api.v1.model.UsuarioModel;
import com.algaworks.algafood.api.v1.openapi.model.CidadeCollectionModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.CozinhaCollectionModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.EstadoCollectionModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.FormaPagamentoCollectionModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.GrupoCollectionModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.LinksModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.PageableModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.PedidoResumoCollectionModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.PermissaoCollectionModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.ProdutoCollectionModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.RestauranteBasicoCollectionModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.UsuarioCollectionModelOpenApi;
import com.algaworks.algafood.api.v2.model.CidadeModelV2;
import com.algaworks.algafood.api.v2.model.CozinhaModelV2;
import com.algaworks.algafood.api.v2.openapi.model.CidadeCollectionModelV2OpenApi;
import com.algaworks.algafood.api.v2.openapi.model.CozinhaCollectionModelV2OpenApi;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.ServletWebRequest;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RepresentationBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.OAuth2Scheme;
import springfox.documentation.service.Response;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
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

    private final TypeResolver typeResolver = new TypeResolver();

    @Bean
    public Docket apiDocketV1() {

        return new Docket(DocumentationType.OAS_30)
                .groupName("V1")
                .select()
                    .apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api"))
                    .paths(PathSelectors.ant("/v1/**"))
                    .build()
                .useDefaultResponseMessages(false)
                .globalResponses(HttpMethod.GET, globalGetResponseMessages())
                .globalResponses(HttpMethod.POST, globalPostResponseMessages())
                .globalResponses(HttpMethod.PUT, globalPutResponseMessages())
                .globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
                .additionalModels(typeResolver.resolve(Problem.class))
                .ignoredParameterTypes(ServletWebRequest.class)
                .directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
                .directModelSubstitute(Links.class, LinksModelOpenApi.class)
                .alternateTypeRules(
                        buildPagedModelTypeRole(PedidoResumoModel.class, PedidoResumoCollectionModelOpenApi.class),
                        buildPagedModelTypeRole(CozinhaModel.class, CozinhaCollectionModelOpenApi.class),
                        buildCollectionModelTypeRole(CidadeModel.class, CidadeCollectionModelOpenApi.class),
                        buildCollectionModelTypeRole(EstadoModel.class, EstadoCollectionModelOpenApi.class),
                        buildCollectionModelTypeRole(FormaPagamentoModel.class, FormaPagamentoCollectionModelOpenApi.class),
                        buildCollectionModelTypeRole(GrupoModel.class, GrupoCollectionModelOpenApi.class),
                        buildCollectionModelTypeRole(PermissaoModel.class, PermissaoCollectionModelOpenApi.class),
                        buildCollectionModelTypeRole(ProdutoModel.class, ProdutoCollectionModelOpenApi.class),
                        buildCollectionModelTypeRole(UsuarioModel.class, UsuarioCollectionModelOpenApi.class),
                        buildCollectionModelTypeRole(RestauranteBasicoModel.class, RestauranteBasicoCollectionModelOpenApi.class))
                .securitySchemes(Arrays.asList(securityScheme()))
                .securityContexts(Arrays.asList(securityContext()))
                .apiInfo(apiInfoV1())
                .tags(new Tag("Cidades", "Gerencia as Cidades"),
                        new Tag("Grupos", "Gerencia os grupos de usuários"),
                        new Tag("Cozinhas", "Gerencia as cozinhas"),
                        new Tag("Formas de Pagamento", "Gerencia as formas de pagamento"),
                        new Tag("Pedidos", "Gerencia os pedidos"),
                        new Tag("Restaurantes", "Gerencia os restaurantes"),
                        new Tag("Estados", "Gerencia os estados"),
                        new Tag("Produtos", "Gerencia os produtos de restaurantes"),
                        new Tag("Usuários", "Gerencia os usuários"),
                        new Tag("Estatísticas", "Estatísticas da AlgaFood"),
                        new Tag("Permissões", "Gerencia as permissões de acesso"));
    }

    @Bean
    public Docket apiDocketV2() {

        return new Docket(DocumentationType.OAS_30)
                    .groupName("V2")
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api"))
                    .paths(PathSelectors.ant("/v2/**"))
                .build()
                .useDefaultResponseMessages(false)
                .globalResponses(HttpMethod.GET, globalGetResponseMessages())
                .globalResponses(HttpMethod.POST, globalPostResponseMessages())
                .globalResponses(HttpMethod.PUT, globalPutResponseMessages())
                .globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
                .additionalModels(typeResolver.resolve(Problem.class))
                .ignoredParameterTypes(ServletWebRequest.class)
                .directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
                .directModelSubstitute(Links.class, LinksModelOpenApi.class)
                .alternateTypeRules(
                        buildPagedModelTypeRole(CozinhaModelV2.class, CozinhaCollectionModelV2OpenApi.class),
                        buildCollectionModelTypeRole(CidadeModelV2.class, CidadeCollectionModelV2OpenApi.class))
                .securitySchemes(Arrays.asList(securityScheme()))
                .securityContexts(Arrays.asList(securityContext()))
                .apiInfo(apiInfoV2())
                .tags(new Tag("Cidades", "Gerencia as Cidades"),
                        new Tag("Cozinhas", "Gerencia as cozinhas"));
    }

    private SecurityScheme securityScheme() {
        return OAuth2Scheme.OAUTH2_PASSWORD_FLOW_BUILDER
                .name("AlgaFood")
                .scopes(scopes())
                .tokenUrl("/oauth/token")
                .build();
    }

    private SecurityContext securityContext() {
        var securityReference = SecurityReference.builder()
                .reference("AlgaFood")
                .scopes(scopes().toArray(new AuthorizationScope[0]))
                .build();

        return SecurityContext.builder()
                .securityReferences(Arrays.asList(securityReference))
                .operationSelector(operationContext -> true)
                .build();
    }

    private List<AuthorizationScope> scopes() {
        return Arrays.asList(new AuthorizationScope("READ", "Acesso de leitura"),
                new AuthorizationScope("WRITE", "Acesso de gravação"));
    }

    @Bean
    public JacksonModuleRegistrar springFoxJacksonConfig() {
        return objectMapper -> objectMapper.registerModule(new JavaTimeModule());
    }

    private <M, P> AlternateTypeRule buildPagedModelTypeRole(Class<M> classModel, Class<P> classPagedModel) {
        return AlternateTypeRules.newRule(
                typeResolver.resolve(PagedModel.class, classModel), classPagedModel);
    }

    private <M, C> AlternateTypeRule buildCollectionModelTypeRole(Class<M> classModel, Class<C> classCollectionModel) {
        return AlternateTypeRules.newRule(
                typeResolver.resolve(CollectionModel.class, classModel), classCollectionModel);
    }

    private ApiInfo apiInfoV1() {
        return new ApiInfoBuilder()
                .title("AlgaFood API")
                .description("API aberta para clientes e restaurantes")
                .version("v1")
                .contact(new Contact("Marcos F. Alves", "https://github.com/marcosfalves", "marcosf_alves@outlook.com"))
                .build();
    }

    private ApiInfo apiInfoV2() {
        return new ApiInfoBuilder()
                .title("AlgaFood API")
                .description("<strong>Versão 2 criada somente com alguns endpoints</strong> para aprender e praticar sobre o versionamento de API. " +
                        "<br>Em uma API real todos endpoints devem ser disponibilizados nas novas versões.")
                .version("v2")
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
