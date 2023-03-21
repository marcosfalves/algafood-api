package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.controller.EstatisticasController.EstatisticasModel;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import org.springframework.http.ResponseEntity;

import java.time.ZoneOffset;
import java.util.List;

public interface EstatisticasControllerOpenApi {

    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, ZoneOffset zoneOffset);

    ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter filtro, ZoneOffset zoneOffset);

    EstatisticasModel estatisticas();
}
