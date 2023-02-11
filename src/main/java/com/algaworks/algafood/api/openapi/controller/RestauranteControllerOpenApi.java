package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.api.model.input.RestauranteInput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Api(tags = "Restaurantes")
public interface RestauranteControllerOpenApi {
    @ApiOperation("Lista os restaurantes")
    List<RestauranteModel> listar();

    @ApiOperation("Busca um restaurante por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "ID do restaurante é inválido",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class)))

    })
    RestauranteModel buscar(@ApiParam("ID de um restaurante") Long restauranteId);

    @ApiOperation("Cadastra um restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Restaurante cadastrado")
    })
    RestauranteModel adicionar(RestauranteInput restauranteInput);

    @ApiOperation("Atualiza um restaurante por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Restaurante atualizado"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class)))

    })
    RestauranteModel atualizar(@ApiParam("ID de um restaurante") Long restauranteId,
                               RestauranteInput restauranteInput);

    @ApiOperation("Ativa um restaurante por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Restaurante ativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class)))
    })
    void ativar(@ApiParam("ID de um restaurante") Long restauranteId);

    @ApiOperation("Inativa um restaurante por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Restaurante inativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class)))
    })
    void inativar(@ApiParam("ID de um restaurante") Long restauranteId);

    @ApiOperation("Ativa múltiplos restaurantes")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Restaurantes ativados com sucesso")
    })
    void ativarMultiplos(@ApiParam("IDs de restaurantes") List<Long> restauranteIds);

    @ApiOperation("Inativa múltiplos restaurantes")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Restaurantes inativados com sucesso")
    })
    void inativarMultiplos(@ApiParam("IDs de restaurantes") List<Long> restauranteIds);

    @ApiOperation("Abre um restaurante por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Restaurante aberto com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class)))
    })
    void abrir(@ApiParam("ID de um restaurante") Long restauranteId);

    @ApiOperation("Fecha um restaurante por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Restaurante fechado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class)))
    })
    void fechar(@ApiParam("ID de um restaurante") Long restauranteId);

    @ApiOperation("Atualiza parcialmente um restaurante por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Restaurante atualizado"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class)))

    })
    RestauranteModel atualizarParcial(@ApiParam("ID de um restaurante") Long restauranteId,
                                      @ApiParam("Campos e valores para serem atualizados") Map<String, Object> campos,
                                      HttpServletRequest request);
}
