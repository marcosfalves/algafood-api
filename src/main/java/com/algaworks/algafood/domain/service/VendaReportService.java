package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;

import java.time.ZoneOffset;

public interface VendaReportService {
    byte[] emitirVendasDiarias(VendaDiariaFilter filter, ZoneOffset zoneOffset);
}
