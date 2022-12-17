package com.algaworks.algafood;

import io.restassured.http.ContentType;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CadastroCozinhaIT {

	@LocalServerPort
	private int port;

	@Test
	public void deveRetornarStatus200_QuandoConsultarCozinhas(){
		enableLoggingOfRequestAndResponseIfValidationFails();

		given()
				.basePath("/cozinhas")
				.port(port)
				.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveConter4Cozinhas_QuandoConsultarCozinhas() {
		enableLoggingOfRequestAndResponseIfValidationFails();

		given()
			.basePath("/cozinhas")
			.port(port)
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", hasSize(4))
				.body("nome", hasItems("Indiana", "Tailandesa"));
	}
}
