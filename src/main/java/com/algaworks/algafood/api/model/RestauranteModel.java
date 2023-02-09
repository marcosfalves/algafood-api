package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteModel {
    @ApiModelProperty(example = "1")
    private Long id;
    @ApiModelProperty(example = "Thai Gourmet")
    private String nome;
    @ApiModelProperty(example = "8.00")
    private BigDecimal taxaFrete;
    private CozinhaModel cozinha;
    private Boolean ativo;
    private Boolean aberto;
    private EnderecoModel endereco;
//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
//    private OffsetDateTime dataCadastro;
}
