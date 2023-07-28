package com.algaworks.algafood.apitest;

import com.algaworks.algafood.utils.ApiTestSecurity;
import com.algaworks.algafood.utils.ResourceUtils;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

class CadastroCozinhaIT extends AbstractIntegrationTest {

	private static final int COZINHA_ID_EXISTENTE = 1;
	private static final int COZINHA_ID_INEXISTENTE = 100;
	private String jsonCorretoCozinhaChinesa;

	@BeforeEach
	void setUp() {
		RestAssuredMockMvc.basePath = "/v1/cozinhas";

		jsonCorretoCozinhaChinesa = ResourceUtils.getContentFromResource(
				"/json/correto/cozinha-chinesa.json");
	}

	@Test
	@WithAnonymousUser
	void deveRetornarStatus403_QuandoUsuarioAutenticadoNaoPossuirPermissao() {
		given()
        .when()
		  .get()
		.then()
		  .statusCode(HttpStatus.FORBIDDEN.value());
	}

	@Test
	@ApiTestSecurity.AuthenticatedRead
	void deveRetornarStatus200_QuandoConsultarCozinhas(){
		given()
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("_embedded.cozinhas", notNullValue());
	}

	@Test
	@WithMockUser(
			authorities = {
					"SCOPE_READ",
					"SCOPE_WRITE",
					"EDITAR_COZINHAS"
			}
	)
	void deveRetornarStatus201_QuandoCadastrarCozinha(){
		given()
			.body(jsonCorretoCozinhaChinesa)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}

	@Test
    @ApiTestSecurity.AuthenticatedRead
	void deveRetornarRespostaEStatusCorretos_QuandoConsultarCozinhaExistente() {
		given()
		.when()
			.get(String.valueOf(COZINHA_ID_EXISTENTE))
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", equalTo("Tailandesa"));
	}

	@Test
    @ApiTestSecurity.AuthenticatedRead
	void deveRetornarStatus404_QuandoConsultarCozinhaInexistente() {
		given()
		.when()
			.get(String.valueOf(COZINHA_ID_INEXISTENTE))
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
}
