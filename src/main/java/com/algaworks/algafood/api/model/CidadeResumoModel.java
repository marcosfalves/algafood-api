package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeResumoModel {
    @ApiModelProperty(example = "3")
    private Long id;
    @ApiModelProperty(example = "Campo Mourão")
    private String nome;
    @ApiModelProperty(example = "Paraná")
    private String estado;
}
