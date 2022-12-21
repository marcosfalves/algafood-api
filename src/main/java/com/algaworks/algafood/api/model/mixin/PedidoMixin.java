package com.algaworks.algafood.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.OffsetDateTime;

public class PedidoMixin {
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime dataCriacao;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime dataConfirmacao;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime dataCancelamento;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime dataEntrega;
}
