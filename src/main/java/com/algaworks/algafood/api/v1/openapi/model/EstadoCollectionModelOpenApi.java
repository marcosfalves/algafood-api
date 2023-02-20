package com.algaworks.algafood.api.v1.openapi.model;

import com.algaworks.algafood.api.v1.model.EstadoModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("EstadoCollectionModel")
@Data
public class EstadoCollectionModelOpenApi {

    private EstadosEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("EstadosEmbeddedModel")
    @Data
    public class EstadosEmbeddedModelOpenApi {
        private List<EstadoModel> estados;
    }
}
