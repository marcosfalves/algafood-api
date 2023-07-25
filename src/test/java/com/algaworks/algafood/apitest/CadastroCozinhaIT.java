package com.algaworks.algafood.apitest;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
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

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(properties = { "spring.config.location=classpath:application-test.yml" })
class CadastroCozinhaIT extends IntegrationTestBase {

	private static final int COZINHA_ID_INEXISTENTE = 100;

	private Cozinha cozinhaExistente;
	private int quantidadeCozinhasCadastradas;
	private String jsonCorretoCozinhaChinesa;

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@BeforeEach
	void setUp() {
		RestAssuredMockMvc.basePath = "/v1/cozinhas";

		jsonCorretoCozinhaChinesa = ResourceUtils.getContentFromResource(
				"/json/correto/cozinha-chinesa.json");

		super.databaseCleaner.clearTables();
		prepararDados();
	}

	@Test
	@ApiTestSecurity.AuthenticatedRead
	public void deveRetornarStatus200_QuandoConsultarCozinhas(){
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}

	@Test
    @ApiTestSecurity.AuthenticatedRead
	public void deveRetornarQuantidadeCorretaDeCozinhas_QuandoConsultarCozinhas() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("_embedded.cozinhas", hasSize(quantidadeCozinhasCadastradas));
	}

	@Test
	@WithMockUser(
			authorities = {
					"SCOPE_READ",
					"SCOPE_WRITE",
					"EDITAR_COZINHAS"
			}
	)
	public void deveRetornarStatus201_QuandoCadastrarCozinha(){
		given()
			.body(jsonCorretoCozinhaChinesa)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}

	@Test
    @ApiTestSecurity.AuthenticatedRead
	public void deveRetornarRespostaEStatusCorretos_QuandoConsultarCozinhaExistente() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get(cozinhaExistente.getId().toString())
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", equalTo(cozinhaExistente.getNome()));
	}

	@Test
    @ApiTestSecurity.AuthenticatedRead
	public void deveRetornarStatus404_QuandoConsultarCozinhaInexistente() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get(String.valueOf(COZINHA_ID_INEXISTENTE))
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}

	private void prepararDados() {
		Cozinha cozinha1 = new Cozinha();
		cozinha1.setNome("Tailandesa");
		cozinhaRepository.save(cozinha1);

		Cozinha cozinha2 = new Cozinha();
		cozinha2.setNome("Americana");
		cozinhaRepository.save(cozinha2);

		cozinhaExistente = cozinha2;
		quantidadeCozinhasCadastradas = (int) cozinhaRepository.count();
	}
}
