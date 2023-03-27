package com.algaworks.algafood.api.v1.openapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Pedidos")
@SecurityRequirement(name = "security_auth")
public interface FluxoPedidoControllerOpenApi {

    @Operation(summary = "Confirmação de pedido",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Pedido confirmado com sucesso")
            }
    )
    ResponseEntity<Void> confirmar(@Parameter(description = "Código do pedido", example = "b5741512-8fbc-47fa-9ac1-b530354fc0ff") String codigoPedido);

    @Operation(summary = "Registrar entrega de pedido",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Entrega de pedido registrada com sucesso")
            }
    )
    ResponseEntity<Void> entregar(@Parameter(description = "Código do pedido", example = "b5741512-8fbc-47fa-9ac1-b530354fc0ff") String codigoPedido);

    @Operation(summary = "Cancelamento de pedido",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Pedido cancelado com sucesso")
            }
    )
    ResponseEntity<Void> cancelar(@Parameter(description = "Código do pedido", example = "b5741512-8fbc-47fa-9ac1-b530354fc0ff") String codigoPedido);
}
