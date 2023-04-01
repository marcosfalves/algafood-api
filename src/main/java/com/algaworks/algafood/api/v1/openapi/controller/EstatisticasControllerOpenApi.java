package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.controller.EstatisticasController.EstatisticasModel;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;

import java.time.ZoneOffset;
import java.util.List;

@Tag(name = "Estatísticas")
@SecurityRequirement(name = "security_auth")
public interface EstatisticasControllerOpenApi {

    @Operation(summary = "Consulta estatísticas de vendas diárias", responses = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = VendaDiaria.class))),
                    @Content(mediaType = "application/pdf", schema = @Schema(type = "string", format = "binary"))
            }),
            @ApiResponse(responseCode = "400", description = "Requisição inválida",
                    content = @Content(schema = @Schema(ref = "Problema")))
    })
    List<VendaDiaria> consultarVendasDiarias(@ParameterObject VendaDiariaFilter filtro,
                                             @Parameter(description = "Deslocamento de horário a ser considerado na consulta em relação ao UTC",
                                                     schema = @Schema(type = "string", defaultValue = "+00:00")) ZoneOffset zoneOffset);

    @Operation(hidden = true)
    ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter filtro, ZoneOffset zoneOffset);

    @Operation(summary = "Estatísticas", hidden = true)
    EstatisticasModel estatisticas();
}
