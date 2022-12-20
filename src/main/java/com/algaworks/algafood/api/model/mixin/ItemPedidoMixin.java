package com.algaworks.algafood.api.model.mixin;

import com.algaworks.algafood.domain.model.Pedido;
import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class ItemPedidoMixin {
    @JsonIgnore
    private Pedido pedido;
}
