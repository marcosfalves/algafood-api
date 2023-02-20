package com.algaworks.algafood.api.v1.openapi.model;

import com.algaworks.algafood.api.v1.model.CozinhaModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("CozinhaCollectionModel")
@Data
public class CozinhaCollectionModelOpenApi {

    private CozinhasEmbeddedModelOpenApi _embedded;
    private Links _links;

    private PageModelOpenApi page;

    @ApiModel("CozinhasEmbeddedModel")
    @Data
    public class CozinhasEmbeddedModelOpenApi {
        private List<CozinhaModel> cozinhas;
    }
}
