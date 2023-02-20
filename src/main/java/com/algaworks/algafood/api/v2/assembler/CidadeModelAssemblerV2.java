package com.algaworks.algafood.api.v2.assembler;

import com.algaworks.algafood.api.v2.ApiLinksV2;
import com.algaworks.algafood.api.v2.controller.CidadeControllerV2;
import com.algaworks.algafood.api.v2.model.CidadeModelV2;
import com.algaworks.algafood.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CidadeModelAssemblerV2
        extends RepresentationModelAssemblerSupport<Cidade, CidadeModelV2> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiLinksV2 apiLinks;

    public CidadeModelAssemblerV2() {
        super(CidadeControllerV2.class, CidadeModelV2.class);
    }

    @Override
    public CidadeModelV2 toModel(Cidade cidade) {
        var cidadeModel = createModelWithId(cidade.getId(), cidade);

        modelMapper.map(cidade, cidadeModel);

        cidadeModel.add(apiLinks.linkToCidades(IanaLinkRelations.COLLECTION.value()));

        return cidadeModel;
    }

    @Override
    public CollectionModel<CidadeModelV2> toCollectionModel(Iterable<? extends Cidade> entities) {
        return super.toCollectionModel(entities)
                .add(apiLinks.linkToCidades());
    }
}
