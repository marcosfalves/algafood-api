package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.ApiLinks;
import com.algaworks.algafood.api.v1.controller.RestauranteController;
import com.algaworks.algafood.api.v1.model.RestauranteBasicoModel;
import com.algaworks.algafood.core.security.ApiSecurity;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteBasicoModelAssembler
        extends RepresentationModelAssemblerSupport<Restaurante, RestauranteBasicoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiLinks apiLinks;

    @Autowired
    private ApiSecurity apiSecurity;

    public RestauranteBasicoModelAssembler() {
        super(RestauranteController.class, RestauranteBasicoModel.class);
    }

    public RestauranteBasicoModel toModel(Restaurante restaurante) {
        var restauranteModel = createModelWithId(restaurante.getId(), restaurante);
        modelMapper.map(restaurante, restauranteModel);

        if (apiSecurity.podeConsultarRestaurantes()) {
            restauranteModel.add(
                    apiLinks.linkToRestaurantes(IanaLinkRelations.COLLECTION.value()));
        }

        if (apiSecurity.podeConsultarCozinhas()) {
            restauranteModel.getCozinha().add(
                    apiLinks.linkToCozinha(restaurante.getCozinha().getId()));
        }

        return restauranteModel;
    }

    @Override
    public CollectionModel<RestauranteBasicoModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
        var collectionModel = super.toCollectionModel(entities);

        if (apiSecurity.podeConsultarRestaurantes()) {
            collectionModel.add(apiLinks.linkToRestaurantes());
        }

        return collectionModel;
    }
}
