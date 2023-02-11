package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.UsuarioModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;

import java.util.List;

@Api(tags = "Restaurantes")
public interface RestauranteUsuarioResponsavelControllerOpenApi {
    @ApiOperation("Lista os usuários responsáveis pelo restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class)))
    })
    List<UsuarioModel> listar(@ApiParam("ID do restaurante") Long restauranteId);

    @ApiOperation("Associação de restaurante com usuário responsável")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Associação realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante ou usuário não encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class)))

    })
    void associarUsuario(@ApiParam("ID do restaurante") Long restauranteId,
                         @ApiParam("ID do usuário") Long usuarioId);

    @ApiOperation("Desassociação de restaurante com usuário responsável")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Desassociação realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante ou usuário não encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class)))

    })
    void desassociarUsuario(@ApiParam("ID do restaurante") Long restauranteId,
                            @ApiParam("ID do usuário") Long usuarioId);
}
