package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.RestauranteApenasNomeModel;
import com.algaworks.algafood.api.v1.model.RestauranteBasicoModel;
import com.algaworks.algafood.api.v1.model.RestauranteModel;
import com.algaworks.algafood.api.v1.model.input.RestauranteInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@SecurityRequirement(name = "security_auth")
public interface RestauranteControllerOpenApi {

    @Operation(parameters = {
            @Parameter(
                    name = "projecao",
                    description = "Nome da projeção",
                    example = "apenas-nome",
                    in = ParameterIn.QUERY
            )
    })
    CollectionModel<RestauranteBasicoModel> listar();

    @Operation(hidden = true)
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
