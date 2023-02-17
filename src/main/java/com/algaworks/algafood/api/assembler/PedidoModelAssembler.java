package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.ApiLinks;
import com.algaworks.algafood.api.controller.CidadeController;
import com.algaworks.algafood.api.controller.FormaPagamentoController;
import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.controller.RestauranteProdutoController;
import com.algaworks.algafood.api.controller.UsuarioController;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PedidoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiLinks apiLinks;

    public PedidoModelAssembler() {
        super(PedidoController.class, PedidoModel.class);
    }

    @Override
    public PedidoModel toModel(Pedido pedido) {
        var pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoModel);

        pedidoModel.add(apiLinks.linkToPedidos());

        pedidoModel.getRestaurante().add(linkTo(
                        methodOn(RestauranteController.class).buscar(pedido.getRestaurante().getId())
                ).withSelfRel()
        );

        pedidoModel.getCliente().add(linkTo(
                        methodOn(UsuarioController.class).buscar(pedido.getCliente().getId())
                ).withSelfRel()
        );

        pedidoModel.getFormaPagamento().add(linkTo(
                methodOn(FormaPagamentoController.class).buscar(pedido.getFormaPagamento().getId(), null)
                ).withSelfRel()
        );

        pedidoModel.getEnderecoEntrega().getCidade().add(linkTo(
                methodOn(CidadeController.class).buscar(pedido.getEnderecoEntrega().getCidade().getId())
                ).withSelfRel()
        );

        pedidoModel.getItens().forEach(itemPedidoModel ->
            itemPedidoModel.add(linkTo(
                    methodOn(RestauranteProdutoController.class)
                            .buscar(pedido.getRestaurante().getId(), itemPedidoModel.getProdutoId())
                    ).withRel("produto")
            )
        );

        return pedidoModel;
    }
}
