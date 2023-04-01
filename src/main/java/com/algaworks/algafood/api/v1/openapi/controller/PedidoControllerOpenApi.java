package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.PedidoModel;
import com.algaworks.algafood.api.v1.model.PedidoResumoModel;
import com.algaworks.algafood.api.v1.model.input.PedidoInput;
import com.algaworks.algafood.core.springdoc.PageableParameter;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

@Tag(name = "Pedidos")
@SecurityRequirement(name = "security_auth")
public interface PedidoControllerOpenApi {

    @Operation(summary = "Pesquisa os pedidos com paginação")
    @PageableParameter
    PagedModel<PedidoResumoModel> pesquisar(@Parameter(hidden = true) Pageable pageable,
                                            @ParameterObject PedidoFilter filtro);

    @Operation(summary = "Busca um pedido por código")
    PedidoModel buscar(@Parameter(description = "Código de um pedido", example = "b5741512-8fbc-47fa-9ac1-b530354fc0ff", required = true) String codigoPedido);

    @Operation(summary = "Registra um novo pedido", responses = {
            @ApiResponse(responseCode = "201", description = "Pedido registrado"),
    })
    PedidoModel adicionar(@RequestBody(description = "Representação de um novo pedido", required = true) PedidoInput pedidoInput);
}
