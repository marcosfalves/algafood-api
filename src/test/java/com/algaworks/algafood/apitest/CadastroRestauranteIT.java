package com.algaworks.algafood.apitest;

import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.utils.ApiTestSecurity;
import com.algaworks.algafood.utils.ResourceUtils;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(properties = { "spring.config.location=classpath:application-test.yml" })
class CadastroRestauranteIT extends IntegrationTestBase {

    private static final String VIOLACAO_DE_REGRA_DE_NEGOCIO_PROBLEM_TYPE = "Violação de regra de negócio";
    private static final String DADOS_INVALIDOS_PROBLEM_TITLE = "Dados inválidos";
    private static final int RESTAURANTE_ID_INEXISTENTE = 100;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    private String jsonRestauranteCorreto;
    private String jsonRestauranteSemFrete;
    private String jsonRestauranteSemCozinha;
    private String jsonRestauranteComCozinhaInexistente;
    private Restaurante restauranteExistente;

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

        databaseCleaner.clearTables();
        prepararDados();
    }

    @Test
    @ApiTestSecurity.AuthenticatedRead
    void deveRetornarRespostaEStatus200_QuandoConsultarRestauranteExistente() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get(restauranteExistente.getId().toString())
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("nome", equalTo(restauranteExistente.getNome()));
    }

    @Test
    @ApiTestSecurity.AuthenticatedRead
    void deveRetornarStatus404_QuandoConsultarRestauranteInexistente() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get(String.valueOf(RESTAURANTE_ID_INEXISTENTE))
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @ApiTestSecurity.AuthenticatedRead
    void deveRetornarStatus200_QuandoConsultarRestaurantes() {
        given()
            .accept(ContentType.JSON)
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
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
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
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
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
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
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
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("title", equalTo(VIOLACAO_DE_REGRA_DE_NEGOCIO_PROBLEM_TYPE));
    }

    private void prepararDados() {
        Estado estado = new Estado();
        estado.setNome("Paraná");
        estado = estadoRepository.save(estado);

        Cidade cidade = new Cidade();
        cidade.setNome("Campo Mourão");
        cidade.setEstado(estado);
        cidadeRepository.save(cidade);

        Cozinha cozinhaTailandesa = new Cozinha();
        cozinhaTailandesa.setNome("Tailandesa");
        cozinhaTailandesa = cozinhaRepository.save(cozinhaTailandesa);

        Cozinha cozinhaIndiana = new Cozinha();
        cozinhaIndiana.setNome("Indiana");
        cozinhaIndiana = cozinhaRepository.save(cozinhaIndiana);

        Restaurante restauranteThai = new Restaurante();
        restauranteThai.setNome("Thai Delivery");
        restauranteThai.setTaxaFrete(new BigDecimal("9.50"));
        restauranteThai.setCozinha(cozinhaTailandesa);
        restauranteThai = restauranteRepository.save(restauranteThai);

        Restaurante restauranteTukTuk = new Restaurante();
        restauranteTukTuk.setNome("Tuk Tuk Comida Indiana");
        restauranteTukTuk.setTaxaFrete(new BigDecimal("15.00"));
        restauranteTukTuk.setCozinha(cozinhaIndiana);
        restauranteRepository.save(restauranteTukTuk);

        restauranteExistente = restauranteThai;
    }
}
