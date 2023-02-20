package com.algaworks.algafood.api.openapi.model;

import com.algaworks.algafood.api.model.RestauranteBasicoModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("RestauranteBasicoCollectionModel")
@Data
public class RestauranteBasicoCollectionModelOpenApi {

    private RestaurantesBasicoEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("RestaurantesBasicoEmbeddedModel")
    @Data
    public class RestaurantesBasicoEmbeddedModelOpenApi {
        private List<RestauranteBasicoModel> restaurantes;
    }
}
