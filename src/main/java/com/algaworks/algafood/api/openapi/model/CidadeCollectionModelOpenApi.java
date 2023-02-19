package com.algaworks.algafood.api.openapi.model;

import com.algaworks.algafood.api.model.CidadeModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("CidadeCollectionModel")
@Data
public class CidadeCollectionModelOpenApi {

    private CidadesEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("CidadesEmbeddedModel")
    @Data
    public class CidadesEmbeddedModelOpenApi {
        private List<CidadeModel> cidades;
    }
}
