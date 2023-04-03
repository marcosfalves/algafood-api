package com.algaworks.algafood.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class VendaDiaria {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date data;
    private Long totalVendas;
    private BigDecimal totalFaturado;

    public VendaDiaria(java.sql.Date data, Long totalVendas, BigDecimal totalFaturado) {
        this.data = new Date(data.getTime());
        this.totalVendas = totalVendas;
        this.totalFaturado = totalFaturado;
    }
}
