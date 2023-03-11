package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.ApiLinks;
import com.algaworks.algafood.api.v1.controller.PedidoController;
import com.algaworks.algafood.api.v1.model.PedidoResumoModel;
import com.algaworks.algafood.core.security.ApiSecurity;
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

    @Autowired
    private ApiSecurity apiSecurity;

    public PedidoResumoModelAssembler() {
        super(PedidoController.class, PedidoResumoModel.class);
    }

    @Override
    public PedidoResumoModel toModel(Pedido pedido) {
        var pedidoResumoModel = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoResumoModel);

        if (apiSecurity.podePesquisarPedidos()) {
            pedidoResumoModel.add(apiLinks.linkToPedidos(IanaLinkRelations.COLLECTION.value()));
        }

        if (apiSecurity.podeConsultarRestaurantes()) {
            pedidoResumoModel.getRestaurante().add(apiLinks.linkToRestaurante(pedido.getRestaurante().getId()));
        }

        if (apiSecurity.podeConsultarControleDeAcesso()) {
            pedidoResumoModel.getCliente().add(apiLinks.linkToUsuario(pedido.getCliente().getId()));
        }

        return pedidoResumoModel;
    }

}
