package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoModel {
    @ApiModelProperty(example = "38400-000")
    private String cep;
    @ApiModelProperty(example = "Rua Floriano Peixoto")
    private String logradouro;
    @ApiModelProperty(example = "\"600\"")
    private String numero;
    @ApiModelProperty(example = "Apto 704")
    private String complemento;
    @ApiModelProperty(example = "Centro")
    private String bairro;
    private CidadeResumoModel cidade;
}
