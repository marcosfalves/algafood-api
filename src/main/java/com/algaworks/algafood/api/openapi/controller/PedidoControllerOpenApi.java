package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.api.model.input.PedidoInput;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;

@Api(tags = "Pedidos")
public interface PedidoControllerOpenApi {
    @ApiOperation("Lista os pedidos com paginação")
    Page<PedidoResumoModel> pesquisar(Pageable pageable,
                                      PedidoFilter filtro);

    @ApiOperation("Busca um pedido por código")
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class)))

    })
    PedidoModel buscar(@ApiParam(value = "Código de um pedido", example = "b5741512-8fbc-47fa-9ac1-b530354fc0ff") String codigoPedido);

    @ApiOperation("Registra um pedido")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pedido registrado")
    })
    PedidoModel adicionar(PedidoInput pedidoInput);
}
