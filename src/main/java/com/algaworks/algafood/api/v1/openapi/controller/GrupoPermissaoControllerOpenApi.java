package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.PermissaoModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

public interface GrupoPermissaoControllerOpenApi {

    CollectionModel<PermissaoModel> listar(Long grupoId);

    ResponseEntity<Void> associarPermissao(Long grupoId, Long permissaoId);

    ResponseEntity<Void> desassociarPermissao(Long grupoId, Long permissaoId);
}
