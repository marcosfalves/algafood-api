package com.algaworks.algafood.api.v1.model;

import com.algaworks.algafood.domain.model.StatusPedido;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Relation(collectionRelation = "pedidos")
@Getter
@Setter
public class PedidoResumoModel extends RepresentationModel<PedidoResumoModel> {
    @ApiModelProperty(example = "b5741512-8fbc-47fa-9ac1-b530354fc0ff")
    private String codigo;
    @ApiModelProperty(example = "100.00")
    private BigDecimal subtotal;
    @ApiModelProperty(example = "8.00")
    private BigDecimal taxaFrete;
    @ApiModelProperty(example = "108.00")
    private BigDecimal valorTotal;
    @ApiModelProperty(example = "ENTREGUE")
    private StatusPedido status;
    @ApiModelProperty(example = "2023-02-07T09:50:07Z")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime dataCriacao;
    private RestauranteApenasNomeModel restaurante;
    private UsuarioModel cliente;
}