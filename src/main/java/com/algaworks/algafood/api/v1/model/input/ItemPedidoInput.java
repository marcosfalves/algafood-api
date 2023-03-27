package com.algaworks.algafood.api.v1.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
public class ItemPedidoInput {
    @Schema(example = "2")
    @NotNull
    private Long produtoId;

    @Schema(example = "4")
    @NotNull
    @PositiveOrZero
    private Integer quantidade;

    @Schema(example = "Sem pimenta")
    private String observacao;
}
