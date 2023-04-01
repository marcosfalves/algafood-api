package com.algaworks.algafood.api.v1.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

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
