package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class EstadoModel extends RepresentationModel {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Paraná")
    private String nome;
}
