package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;

import java.time.ZoneOffset;
import java.util.List;

public interface VendaQueryService {
    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filter, ZoneOffset zoneOffset);
}
