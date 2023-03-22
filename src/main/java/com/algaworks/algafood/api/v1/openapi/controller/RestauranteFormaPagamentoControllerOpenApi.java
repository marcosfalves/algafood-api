package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.FormaPagamentoModel;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = "security_auth")
public interface RestauranteFormaPagamentoControllerOpenApi {

    CollectionModel<FormaPagamentoModel> listar(Long restauranteId);

    ResponseEntity<Void> associarFormaPagamento(Long restauranteId, Long formaPagamentoId);

    ResponseEntity<Void> desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId);
}
