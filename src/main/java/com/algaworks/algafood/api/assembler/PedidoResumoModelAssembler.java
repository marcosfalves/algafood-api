package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.ApiLinks;
import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PedidoResumoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiLinks apiLinks;

    public PedidoResumoModelAssembler() {
        super(PedidoController.class, PedidoResumoModel.class);
    }

    @Override
    public PedidoResumoModel toModel(Pedido pedido) {
        var pedidoResumoModel = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoResumoModel);

        pedidoResumoModel.add(apiLinks.linkToPedidos(IanaLinkRelations.COLLECTION.value()));

        pedidoResumoModel.getRestaurante().add(apiLinks.linkToRestaurante(pedido.getRestaurante().getId()));

        pedidoResumoModel.getCliente().add(apiLinks.linkToUsuario(pedido.getCliente().getId()));

        return pedidoResumoModel;
    }

}
