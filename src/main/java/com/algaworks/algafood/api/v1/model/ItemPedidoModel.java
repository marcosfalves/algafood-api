package com.algaworks.algafood.api.v1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemPedidoModel extends RepresentationModel<ItemPedidoModel> {
    @Schema(example = "2")
    private Long produtoId;

    @Schema(example = "Camarão tailandês")
    private String produtoNome;

    @Schema(example = "4")
    private Integer quantidade;

    @Schema(example = "25.00")
    private BigDecimal precoUnitario;

    @Schema(example = "100.00")
    private BigDecimal precoTotal;

    @Schema(example = "Sem pimenta")
    private String observacao;
}
