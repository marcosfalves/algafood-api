package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.UsuarioModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

public interface RestauranteUsuarioResponsavelControllerOpenApi {

    CollectionModel<UsuarioModel> listar(Long restauranteId);

    ResponseEntity<Void> associarUsuario(Long restauranteId, Long usuarioId);

    ResponseEntity<Void> desassociarUsuario(Long restauranteId, Long usuarioId);
}
