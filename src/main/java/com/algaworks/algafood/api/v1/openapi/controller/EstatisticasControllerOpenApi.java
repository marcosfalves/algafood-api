package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.controller.EstatisticasController.EstatisticasModel;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.time.ZoneOffset;
import java.util.List;

@Api(tags = "Estatísticas")
public interface EstatisticasControllerOpenApi {

    @ApiOperation("Consulta estatísticas de vendas diárias")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "restauranteId", paramType = "query", value = "ID do restaurante",
                    example = "1", dataTypeClass = Long.class),
            @ApiImplicitParam(name = "dataCriacaoInicio", paramType = "query", value = "Data inicial da criação do pedido",
                    example = "2023-02-11", dataTypeClass = Date.class),
            @ApiImplicitParam(name = "dataCriacaoFim", paramType = "query", value = "Data final da criação do pedido",
                    example = "2023-02-11", dataTypeClass = Date.class)
    })
    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro,

                                             @ApiParam(value = "Deslocamento de horário a ser considerado na consulta em relação ao UTC",
                                                     defaultValue = "+00:00")
                                             ZoneOffset zoneOffset);

    ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter filtro,
                                                     ZoneOffset zoneOffset);

    @ApiOperation(value = "Estatísticas", hidden = true)
    EstatisticasModel estatisticas();
}
