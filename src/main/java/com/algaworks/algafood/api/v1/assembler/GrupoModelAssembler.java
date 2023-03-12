package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.ApiLinks;
import com.algaworks.algafood.api.v1.controller.GrupoController;
import com.algaworks.algafood.api.v1.model.GrupoModel;
import com.algaworks.algafood.core.security.ApiSecurity;
import com.algaworks.algafood.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class GrupoModelAssembler
        extends RepresentationModelAssemblerSupport<Grupo, GrupoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiLinks apiLinks;

    @Autowired
    private ApiSecurity apiSecurity;

    public GrupoModelAssembler() {
        super(GrupoController.class, GrupoModel.class);
    }

    @Override
    public GrupoModel toModel(Grupo grupo) {
        var grupoModel = createModelWithId(grupo.getId(), grupo);
        modelMapper.map(grupo, grupoModel);

        if (apiSecurity.podeConsultarControleDeAcesso()) {
            grupoModel.add(
                    apiLinks.linkToGrupos(IanaLinkRelations.COLLECTION.value()));

            grupoModel.add(
                    apiLinks.linkToGrupoPermissoes(grupo.getId(), "permissoes"));
        }

        return grupoModel;
    }

    @Override
    public CollectionModel<GrupoModel> toCollectionModel(Iterable<? extends Grupo> entities) {
        var collectionModel = super.toCollectionModel(entities);

        if (apiSecurity.podeConsultarControleDeAcesso()) {
            collectionModel.add(apiLinks.linkToGrupos());
        }

        return collectionModel;
    }
}
