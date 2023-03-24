package com.algaworks.algafood.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.List;

@Schema(name = "Problema")
@JsonInclude(Include.NON_NULL)
@Getter
@ToString
@Builder
public class Problem {

    @Schema(example = "400")
    private Integer status;

    @Schema(example = "2023-03-24T10:06:21Z")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime timestamp;

    @Schema(example = "https://algafood-api.malves.dev.br/dados-invalidos")
    private String type;

    @Schema(example = "Dados inválidos")
    private String title;

    @Schema(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.")
    private String detail;

    @Schema(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.")
    private String userMessage;

    @Schema(description = "Lista de objetos ou campos que geraram o erro (opcional)")
    private List<Object> objects;

    @Schema(name = "ObjetoProblema")
    @Getter
    @ToString
    @Builder
    public static class Object {

        @Schema(example = "estado.id")
        private String name;

        @Schema(example = "Código do estado é obrigatório")
        private String userMessage;
    }
}
