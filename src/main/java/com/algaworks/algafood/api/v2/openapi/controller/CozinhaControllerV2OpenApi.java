package com.algaworks.algafood.api.v2.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v2.model.CozinhaModelV2;
import com.algaworks.algafood.api.v2.model.input.CozinhaInputV2;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;

@Api(tags = "Cozinhas")
public interface CozinhaControllerV2OpenApi {
    @ApiOperation("Lista as cozinhas com paginação")
    PagedModel<CozinhaModelV2> listar(Pageable pageable);

    @ApiOperation("Busca uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "ID da cozinha é inválido",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Cozinha não encontrada",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class)))

    })
    CozinhaModelV2 buscar(@ApiParam("ID de uma cozinha") Long cozinhaId);

    @ApiOperation("Cadastra uma cozinha")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cozinha cadastrada")
    })
    CozinhaModelV2 adicionar(CozinhaInputV2 cozinhaInput);

    @ApiOperation("Atualiza uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cozinha atualizada"),
            @ApiResponse(responseCode = "404", description = "Cozinha não encontrada",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class)))

    })
    CozinhaModelV2 atualizar(@ApiParam("ID de uma cozinha") Long cozinhaId,
                             CozinhaInputV2 cozinhaInput);

    @ApiOperation("Exclui uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cozinha excluída"),
            @ApiResponse(responseCode = "404", description = "Cozinha não encontrada",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class)))

    })
    void remover(@ApiParam("ID de uma cozinha") Long cozinhaId);
}
