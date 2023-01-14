package com.algaworks.algafood.infrastructure.service.report;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.service.VendaReportService;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;

@Service
public class PdfVendaReportService implements VendaReportService {
    @Override
    public byte[] emitirVendasDiarias(VendaDiariaFilter filter, ZoneOffset zoneOffset) {
        return new byte[0];
    }
}
