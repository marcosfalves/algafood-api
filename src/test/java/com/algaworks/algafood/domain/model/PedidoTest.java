package com.algaworks.algafood.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PedidoTest {

    private Pedido pedido;

    @BeforeEach
    void setUp() {
        pedido = new Pedido();
    }

    @Test
    void calcularValorTotal() {
        var restaurante = new Restaurante();
        restaurante.setTaxaFrete(BigDecimal.ZERO);

        var item = new ItemPedido();
        item.setPrecoUnitario(new BigDecimal("10.00"));
        item.setQuantidade(2);

        pedido.setRestaurante(restaurante);
        pedido.setItens(List.of(item));

        pedido.calcularValorTotal();

        assertEquals(new BigDecimal("20.00"), pedido.getValorTotal());
    }
}