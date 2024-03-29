package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.ApiLinks;
import com.algaworks.algafood.api.v1.controller.PedidoController;
import com.algaworks.algafood.api.v1.model.PedidoModel;
import com.algaworks.algafood.core.security.ApiSecurity;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PedidoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiLinks apiLinks;

    @Autowired
    private ApiSecurity apiSecurity;

    public PedidoModelAssembler() {
        super(PedidoController.class, PedidoModel.class);
    }

    @Override
    public PedidoModel toModel(Pedido pedido) {
        var pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoModel);

        if (apiSecurity.podePesquisarPedidos()) {
            pedidoModel.add(apiLinks.linkToPedidos(IanaLinkRelations.COLLECTION.value()));
        }

        if (apiSecurity.podeGerenciarPedidos(pedido.getCodigo())) {
            if (pedido.podeSerConfirmado()) {
                pedidoModel.add(apiLinks.linkToConfirmacaoPedido(pedido.getCodigo(), "confirmar"));
            }
            if (pedido.podeSerEntregue()) {
                pedidoModel.add(apiLinks.linkToEntregaPedido(pedido.getCodigo(), "entregar"));
            }
            if (pedido.podeSerCancelado()) {
                pedidoModel.add(apiLinks.linkToCancelamentoPedido(pedido.getCodigo(), "cancelar"));
            }
        }

        if (apiSecurity.podeConsultarRestaurantes()) {
            pedidoModel.getRestaurante().add(
                    apiLinks.linkToRestaurante(pedido.getRestaurante().getId()));

            // Quem pode consultar restaurantes, também pode consultar os produtos dos restaurantes
            pedidoModel.getItens().forEach(itemPedidoModel ->
                    itemPedidoModel.add(apiLinks.linkToProduto(
                            pedido.getRestaurante().getId(), itemPedidoModel.getProdutoId(), "produto")
                    )
            );
        }

        if (apiSecurity.podeConsultarControleDeAcesso()) {
            pedidoModel.getCliente().add(
                    apiLinks.linkToUsuario(pedido.getCliente().getId()));
        }

        if (apiSecurity.podeConsultarFormasPagamento()) {
            pedidoModel.getFormaPagamento().add(
                    apiLinks.linkToFormaPagamento(pedido.getFormaPagamento().getId()));
        }

        if (apiSecurity.podeConsultarCidades()) {
            pedidoModel.getEnderecoEntrega().getCidade().add(
                    apiLinks.linkToCidade(pedido.getEnderecoEntrega().getCidade().getId()));
        }

        return pedidoModel;
    }
}
