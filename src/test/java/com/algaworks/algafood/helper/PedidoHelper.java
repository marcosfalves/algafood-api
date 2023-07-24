package com.algaworks.algafood.helper;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;

public class PedidoHelper {

    public static Pedido builder() {
        Restaurante restaurante = new Restaurante();
        restaurante.setNome("Restaurante mock");

        Usuario cliente = new Usuario();
        cliente.setNome("Cliente mock");
        cliente.setEmail("user@mock.com");

        Pedido pedido = new Pedido();
        pedido.setRestaurante(restaurante);
        pedido.setCliente(cliente);

        return pedido;
    }

}
