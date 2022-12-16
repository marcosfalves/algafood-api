package com.algaworks.algafood;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CadastroCozinhaIT {

	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@Test
	public void deveAtribuirId_QuandoCadastrarCozinhaComDadosCorretos(){
		//cenário
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Chinesa");

		//ação
		novaCozinha = cadastroCozinha.salvar(novaCozinha);

		//Validação
		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();
	}

	@Test
	public void deveFalhar_QuandoCadastrarCozinhaSemNome(){
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome(null);

		ConstraintViolationException erroEsperado =
				assertThrows(ConstraintViolationException.class,
						() -> cadastroCozinha.salvar(novaCozinha));

		assertThat(erroEsperado).isNotNull();
	}

	@Test
	public void deveFalhar_QuandoExcluirCozinhaEmUso(){
		Long idCozinhaEmUso = 1L;

		EntidadeEmUsoException erroEsperado =
				assertThrows(EntidadeEmUsoException.class,
						() -> cadastroCozinha.excluir(idCozinhaEmUso));

		assertThat(erroEsperado).isNotNull();
	}

	@Test
	public void deveFalhar_QuandoExcluirCozinhaInexistente(){
		Long idCozinhaInexistente = 9999L;

		CozinhaNaoEncontradaException erroEsperado =
				assertThrows(CozinhaNaoEncontradaException.class,
						() -> cadastroCozinha.excluir(idCozinhaInexistente));

		assertThat(erroEsperado).isNotNull();
	}
}
