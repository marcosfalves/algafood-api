package com.algaworks.algafood.api.v1.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SenhaInput {
    @Schema(example = "123")
    @NotBlank
    private String senhaAtual;

    @Schema(example = "456")
    @NotBlank
    private String novaSenha;
}
