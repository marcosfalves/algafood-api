package com.algaworks.algafood.domain.filter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class VendaDiariaFilter {
    private Long restauranteId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataCriacaoInicio;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataCriacaoFim;
}
