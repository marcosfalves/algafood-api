package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.RestauranteApenasNomeModel;
import com.algaworks.algafood.api.v1.model.RestauranteBasicoModel;
import com.algaworks.algafood.api.v1.model.RestauranteModel;
import com.algaworks.algafood.api.v1.model.input.RestauranteInput;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface RestauranteControllerOpenApi {

    CollectionModel<RestauranteBasicoModel> listar();

    CollectionModel<RestauranteApenasNomeModel> listarApenasNome();

    RestauranteModel buscar(Long restauranteId);

    RestauranteModel adicionar(RestauranteInput restauranteInput);

    RestauranteModel atualizar(Long restauranteId, RestauranteInput restauranteInput);

    ResponseEntity<Void> ativar(Long restauranteId);

    ResponseEntity<Void> inativar(Long restauranteId);

    void ativarMultiplos(List<Long> restauranteIds);

    void inativarMultiplos(List<Long> restauranteIds);

    ResponseEntity<Void> abrir(Long restauranteId);

    ResponseEntity<Void> fechar(Long restauranteId);

    RestauranteModel atualizarParcial(Long restauranteId, Map<String, Object> campos, HttpServletRequest request);
}
