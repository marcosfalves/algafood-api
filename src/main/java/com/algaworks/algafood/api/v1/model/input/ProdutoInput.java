package com.algaworks.algafood.api.v1.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoInput {
    @Schema(example = "Espetinho de Cupim")
    @NotBlank
    private String nome;

    @Schema(example = "Acompanha farinha, mandioca e vinagrete")
    @NotBlank
    private String descricao;

    @Schema(example = "12.99")
    @NotNull
    @PositiveOrZero
    private BigDecimal preco;

    @Schema(example = "true")
    @NotNull
    private Boolean ativo;
}
