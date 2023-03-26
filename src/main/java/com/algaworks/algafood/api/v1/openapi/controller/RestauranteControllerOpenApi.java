package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.RestauranteApenasNomeModel;
import com.algaworks.algafood.api.v1.model.RestauranteBasicoModel;
import com.algaworks.algafood.api.v1.model.RestauranteModel;
import com.algaworks.algafood.api.v1.model.input.RestauranteInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Tag(name = "Restaurantes")
@SecurityRequirement(name = "security_auth")
public interface RestauranteControllerOpenApi {

    @Operation(
            summary = "Lista os restaurantes",
            parameters = {
            @Parameter(
                    name = "projecao",
                    description = "Nome da projeção",
                    example = "apenas-nome",
                    in = ParameterIn.QUERY
            )
    })
    CollectionModel<RestauranteBasicoModel> listar();

    @Operation(summary = "Lista os restaurantes apenas com atributo nome", hidden = true)
    CollectionModel<RestauranteApenasNomeModel> listarApenasNome();

    @Operation(summary = "Busca um restaurante por ID",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "400", description = "ID do restaurante é inválido",
                            content = @Content(schema = @Schema(ref = "Problema"))),
                    @ApiResponse(responseCode = "404", description = "Restaurante não encontrado",
                            content = @Content(schema = @Schema(ref = "Problema")))
            })
    RestauranteModel buscar(@Parameter(description = "ID de um restaurante", example = "1", required = true) Long restauranteId);

    @Operation(summary = "Cadastra um restaurante")
    RestauranteModel adicionar(@RequestBody(description = "Representação de um novo restaurante", required = true) RestauranteInput restauranteInput);

    @Operation(summary = "Atualiza um restaurante por ID")
    RestauranteModel atualizar(@Parameter(description = "ID de um restaurante", example = "1", required = true) Long restauranteId,
                               @RequestBody(description = "Representação de um restaurante com dados atualizados", required = true) RestauranteInput restauranteInput);

    @Operation(summary = "Ativa um restaurante por ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Restaurante ativado com sucesso")
            })
    ResponseEntity<Void> ativar(@Parameter(description = "ID de um restaurante", example = "1", required = true) Long restauranteId);

    @Operation(summary = "Inativa um restaurante por ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Restaurante inativado com sucesso")
            })
    ResponseEntity<Void> inativar(@Parameter(description = "ID de um restaurante", example = "1", required = true) Long restauranteId);

    @Operation(summary = "Ativa múltiplos restaurantes",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Restaurantes ativados com sucesso")
            })
    void ativarMultiplos(@RequestBody(description = "IDs de restaurantes", required = true) List<Long> restauranteIds);

    @Operation(summary = "Inativa múltiplos restaurantes",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Restaurantes inativados com sucesso")
            })
    void inativarMultiplos(@RequestBody(description = "IDs de restaurantes", required = true) List<Long> restauranteIds);

    @Operation(summary = "Abre um restaurante por ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Restaurante aberto com sucesso")
            })
    ResponseEntity<Void> abrir(@Parameter(description = "ID de um restaurante", example = "1", required = true) Long restauranteId);

    @Operation(summary = "Fecha um restaurante por ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Restaurante fechado com sucesso")
            })
    ResponseEntity<Void> fechar(@Parameter(description = "ID de um restaurante", example = "1", required = true) Long restauranteId);

    @Operation(summary = "Atualiza parcialmente um restaurante por ID")
    RestauranteModel atualizarParcial(@Parameter(description = "ID de um restaurante", example = "1", required = true) Long restauranteId,
                                      @RequestBody(description = "Campos e valores para serem atualizados", required = true) Map<String, Object> campos,
                                      @Parameter(hidden = true) HttpServletRequest request);
}
