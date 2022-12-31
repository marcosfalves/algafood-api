package com.algaworks.algafood.domain.exception;

public class SenhaInvalidaException extends NegocioException {
    public SenhaInvalidaException(String mensagem) {
        super(mensagem);
    }
}
