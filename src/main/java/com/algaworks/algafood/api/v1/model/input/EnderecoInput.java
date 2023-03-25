package com.algaworks.algafood.api.v1.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EnderecoInput {
    @Schema(example = "38400-000")
    @NotBlank
    private String cep;

    @Schema(example = "Rua Floriano Peixoto")
    @NotBlank
    private String logradouro;

    @Schema(example = "600")
    @NotBlank
    private String numero;

    @Schema(example = "Apto 704")
    private String complemento;

    @Schema(example = "Centro")
    @NotBlank
    private String bairro;

    @Valid
    @NotNull
    private CidadeIdInput cidade;
}
