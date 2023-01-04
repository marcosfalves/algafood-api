package com.algaworks.algafood.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {
    public PedidoNaoEncontradoException(String mensagem){
        super(mensagem);
    }

    public PedidoNaoEncontradoException(Long pedidoId) {
        this(String.format("Não existe um pedido com código %d", pedidoId));
    }
}
