package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.FormaPagamentoModel;
import com.algaworks.algafood.api.v1.model.input.FormaPagamentoInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

@Tag(name = "Formas de Pagamento")
@SecurityRequirement(name = "security_auth")
public interface FormaPagamentoControllerOpenApi {

    @Operation(summary = "Lista as formas de pagamento")
    ResponseEntity<CollectionModel<FormaPagamentoModel>> listar(@Parameter(hidden = true) ServletWebRequest webRequest);

    @Operation(summary = "Busca uma forma de pagamento por ID",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "400", description = "ID da forma de pagamento é inválido",
                            content = @Content(schema = @Schema(ref = "Problema")))
            })
    ResponseEntity<FormaPagamentoModel> buscar(@Parameter(description = "ID de uma forma de pagamento", example = "1", required = true) Long formaPagamentoId,
                                               @Parameter(hidden = true) ServletWebRequest webRequest);

    @Operation(summary = "Cadastra uma forma de pagamento")
    FormaPagamentoModel adicionar(@RequestBody(description = "Representação de uma nova forma de pagamento", required = true) FormaPagamentoInput formaPagamentoInput);

    @Operation(summary = "Atualiza uma forma de pagamento por ID")
    FormaPagamentoModel atualizar(@Parameter(description = "ID de uma forma de pagamento", example = "1", required = true) Long formaPagamentoId,
                                  @RequestBody(description = "Representação de uma forma de pagamento com dados atualizados", required = true) FormaPagamentoInput formaPagamentoInput);

    @Operation(summary = "Exclui uma forma de pagamento por ID")
    void remover(@Parameter(description = "ID de uma forma de pagamento", example = "1", required = true) Long formaPagamentoId);
}
