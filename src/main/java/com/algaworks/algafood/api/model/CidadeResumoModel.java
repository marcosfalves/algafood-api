package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cidades")
@Getter
@Setter
public class CidadeResumoModel extends RepresentationModel<CidadeResumoModel> {
    @ApiModelProperty(example = "3")
    private Long id;
    @ApiModelProperty(example = "Campo Mourão")
    private String nome;
    @ApiModelProperty(example = "Paraná")
    private String estado;
}
