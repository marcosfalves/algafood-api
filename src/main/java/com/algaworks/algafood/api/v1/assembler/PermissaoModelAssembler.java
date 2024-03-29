package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.ApiLinks;
import com.algaworks.algafood.api.v1.model.PermissaoModel;
import com.algaworks.algafood.core.security.ApiSecurity;
import com.algaworks.algafood.domain.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class PermissaoModelAssembler
        implements RepresentationModelAssembler<Permissao, PermissaoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiLinks apiLinks;

    @Autowired
    private ApiSecurity apiSecurity;

    @Override
    public PermissaoModel toModel(Permissao permissao) {
        var permissaoModel = modelMapper.map(permissao, PermissaoModel.class);
        return permissaoModel;
    }

    @Override
    public CollectionModel<PermissaoModel> toCollectionModel(Iterable<? extends Permissao> entities) {
        var collectionModel = RepresentationModelAssembler.super.toCollectionModel(entities);

        if (apiSecurity.podeConsultarControleDeAcesso()) {
            collectionModel.add(apiLinks.linkToPermissoes());
        }

        return collectionModel;
    }
}
