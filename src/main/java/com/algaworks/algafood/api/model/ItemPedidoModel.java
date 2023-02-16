package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemPedidoModel extends RepresentationModel<ItemPedidoModel> {
    @ApiModelProperty(example = "2")
    private Long produtoId;

    @ApiModelProperty(example = "Camarão tailandês")
    private String produtoNome;
    @ApiModelProperty(example = "4")
    private Integer quantidade;

    @ApiModelProperty(example = "25.00")
    private BigDecimal precoUnitario;

    @ApiModelProperty(example = "100.00")
    private BigDecimal precoTotal;

    @ApiModelProperty(example = "Sem pimenta")
    private String observacao;
}
