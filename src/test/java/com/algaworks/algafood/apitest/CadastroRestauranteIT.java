package com.algaworks.algafood.apitest;

import com.algaworks.algafood.utils.ApiTestSecurity;
import com.algaworks.algafood.utils.ResourceUtils;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;

class CadastroRestauranteIT extends AbstractIntegrationTest {

    private static final String VIOLACAO_DE_REGRA_DE_NEGOCIO_PROBLEM_TYPE = "Violação de regra de negócio";
    private static final String DADOS_INVALIDOS_PROBLEM_TITLE = "Dados inválidos";

    private static final int RESTAURANTE_ID_EXISTENTE = 1;
    private static final int RESTAURANTE_ID_INEXISTENTE = 100;

    private String jsonRestauranteCorreto;
    private String jsonRestauranteSemFrete;
    private String jsonRestauranteSemCozinha;
    private String jsonRestauranteComCozinhaInexistente;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.basePath = "/v1/restaurantes";

        jsonRestauranteCorreto = ResourceUtils.getContentFromResource(
                "/json/correto/restaurante-thai-gourmet.json");

        jsonRestauranteSemFrete = ResourceUtils.getContentFromResource(
                "/json/incorreto/restaurante-thai-gourmet-sem-frete.json");

        jsonRestauranteSemCozinha = ResourceUtils.getContentFromResource(
                "/json/incorreto/restaurante-thai-gourmet-sem-cozinha.json");

        jsonRestauranteComCozinhaInexistente = ResourceUtils.getContentFromResource(
                "/json/incorreto/restaurante-thai-gourmet-com-cozinha-inexistente.json");
    }

    @Test
    @ApiTestSecurity.AuthenticatedRead
    void deveRetornarRespostaEStatus200_QuandoConsultarRestauranteExistente() {
        given()
        .when()
            .get(String.valueOf(RESTAURANTE_ID_EXISTENTE))
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("nome", equalTo("Thai Gourmet"));
    }

    @Test
    @ApiTestSecurity.AuthenticatedRead
    void deveRetornarStatus404_QuandoConsultarRestauranteInexistente() {
        given()
        .when()
            .get(String.valueOf(RESTAURANTE_ID_INEXISTENTE))
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @ApiTestSecurity.AuthenticatedRead
    void deveRetornarStatus200_QuandoConsultarRestaurantes() {
        given()
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    @WithMockUser(
            authorities = {
                    "SCOPE_READ",
                    "SCOPE_WRITE",
                    "EDITAR_RESTAURANTES"
            }
    )
    void deveRetornarStatus201_QuandoCadastrarRestaurante() {
        given()
            .body(jsonRestauranteCorreto)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    @WithMockUser(
            authorities = {
                    "SCOPE_READ",
                    "SCOPE_WRITE",
                    "EDITAR_RESTAURANTES"
            }
    )
    void deveRetornarStatus400_QuandoCadastrarRestauranteSemTaxaFrete() {
        given()
            .body(jsonRestauranteSemFrete)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TITLE));
    }

    @Test
    @WithMockUser(
            authorities = {
                    "SCOPE_READ",
                    "SCOPE_WRITE",
                    "EDITAR_RESTAURANTES"
            }
    )
    void deveRetornarStatus400_QuandoCadastrarRestauranteSemCozinha() {
        given()
            .body(jsonRestauranteSemCozinha)
        .when()
            .post()
        .then()
           .statusCode(HttpStatus.BAD_REQUEST.value())
           .body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TITLE));
    }

    @Test
    @WithMockUser(
            authorities = {
                    "SCOPE_READ",
                    "SCOPE_WRITE",
                    "EDITAR_RESTAURANTES"
            }
    )
    void deveRetornarStatus400_QuandoCadastrarRestauranteComCozinhaInexistente() {
        given()
            .body(jsonRestauranteComCozinhaInexistente)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("title", equalTo(VIOLACAO_DE_REGRA_DE_NEGOCIO_PROBLEM_TYPE));
    }
}
