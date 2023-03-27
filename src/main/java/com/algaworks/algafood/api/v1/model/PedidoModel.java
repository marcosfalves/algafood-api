package com.algaworks.algafood.api.v1.model;

import com.algaworks.algafood.domain.model.StatusPedido;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Relation(collectionRelation = "pedidos")
@Getter
@Setter
public class PedidoModel extends RepresentationModel<PedidoModel> {
    @Schema(example = "b5741512-8fbc-47fa-9ac1-b530354fc0ff")
    private String codigo;

    @Schema(example = "100.00")
    private BigDecimal subtotal;

    @Schema(example = "8.00")
    private BigDecimal taxaFrete;

    @Schema(example = "108.00")
    private BigDecimal valorTotal;

    @Schema(example = "ENTREGUE")
    private StatusPedido status;

    @Schema(example = "2023-03-25T09:50:07Z")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime dataCriacao;

    @Schema(example = "2023-03-25T09:55:07Z")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime dataConfirmacao;

    @Schema(example = "2023-03-25T10:30:07Z")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime dataEntrega;

    @Schema(example = "2023-02-07T09:50:07Z")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime dataCancelamento;

    private RestauranteApenasNomeModel restaurante;
    private UsuarioModel cliente;
    private FormaPagamentoModel formaPagamento;
    private EnderecoModel enderecoEntrega;
    private List<ItemPedidoModel> itens;
}
