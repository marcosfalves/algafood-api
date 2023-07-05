package com.algaworks.algafood.apitest;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.utils.DatabaseCleaner;
import com.algaworks.algafood.utils.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "spring.config.location=classpath:application-test.yml" })
class CadastroRestauranteIT {

    private static final String VIOLACAO_DE_REGRA_DE_NEGOCIO_PROBLEM_TYPE = "Violação de regra de negócio";
    private static final String DADOS_INVALIDOS_PROBLEM_TITLE = "Dados inválidos";
    private static final int RESTAURANTE_ID_INEXISTENTE = 100;

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    private String jsonRestauranteCorreto;
    private String jsonRestauranteSemFrete;
    private String jsonRestauranteSemCozinha;
    private String jsonRestauranteComCozinhaInexistente;

    private Restaurante restauranteExistente;

    @BeforeEach
    void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.basePath = "/v1/restaurantes";
        RestAssured.port = port;

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
    public void deveRetornarRespostaEStatus200_QuandoConsultarRestauranteExistente(){
        given()
            .pathParam("restauranteId", restauranteExistente.getId())
            .accept(ContentType.JSON)
        .when()
            .get("/{restauranteId}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("nome", equalTo(restauranteExistente.getNome()));
    }

    @Test
    public void deveRetornarStatus404_QuandoConsultarRestauranteInexistente(){
        given()
            .pathParam("restauranteId", RESTAURANTE_ID_INEXISTENTE)
            .accept(ContentType.JSON)
        .when()
            .get("/{restauranteId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void deveRetornarStatus200_QuandoConsultarRestaurantes() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveRetornarStatus201_QuandoCadastrarRestaurante() {
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
    public void deveRetornarStatus400_QuandoCadastrarRestauranteSemTaxaFrete() {
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
    public void deveRetornarStatus400_QuandoCadastrarRestauranteSemCozinha() {
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
    public void deveRetornarStatus400_QuandoCadastrarRestauranteComCozinhaInexistente() {
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
