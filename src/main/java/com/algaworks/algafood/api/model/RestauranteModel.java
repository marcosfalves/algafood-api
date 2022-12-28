package com.algaworks.algafood.api.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteModel {
    private Long id;
    private String nome;
    private BigDecimal taxaFrete;
    private CozinhaModel cozinha;
    private Boolean ativo;
//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
//    private OffsetDateTime dataCadastro;
}
