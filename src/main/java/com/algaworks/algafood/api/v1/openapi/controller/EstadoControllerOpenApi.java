package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.EstadoModel;
import com.algaworks.algafood.api.v1.model.input.EstadoInput;
import org.springframework.hateoas.CollectionModel;

public interface EstadoControllerOpenApi {
    CollectionModel<EstadoModel> listar();

    EstadoModel buscar(Long estadoId);

    EstadoModel adicionar(EstadoInput estadoInput);

    EstadoModel atualizar(Long estadoId, EstadoInput estadoInput);

    void remover(Long estadoId);
}
