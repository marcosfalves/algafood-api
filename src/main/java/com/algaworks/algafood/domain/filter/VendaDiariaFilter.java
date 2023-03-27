package com.algaworks.algafood.domain.filter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class VendaDiariaFilter {
    @Schema(description = "ID do restaurante para filtro da pesquisa", example = "1")
    private Long restauranteId;

    @Schema(example = "2023-03-25", description = "Data inicial da criação do pedido")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataCriacaoInicio;

    @Schema(example = "2023-03-27", description = "Data final da criação do pedido")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataCriacaoFim;
}
