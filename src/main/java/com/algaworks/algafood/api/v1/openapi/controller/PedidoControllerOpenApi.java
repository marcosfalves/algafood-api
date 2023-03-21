package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.PedidoModel;
import com.algaworks.algafood.api.v1.model.PedidoResumoModel;
import com.algaworks.algafood.api.v1.model.input.PedidoInput;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

public interface PedidoControllerOpenApi {
    PagedModel<PedidoResumoModel> pesquisar(Pageable pageable, PedidoFilter filtro);

    PedidoModel buscar(String codigoPedido);

    PedidoModel adicionar(PedidoInput pedidoInput);
}
