package com.algaworks.algafood.api.v1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "fotos")
@Getter
@Setter
public class FotoProdutoModel extends RepresentationModel<FotoProdutoModel> {
    @Schema(example = "3f016752-8c0f-4578-a5bf-742ec341f898_Prime-Rib.jpg")
    private String nomeArquivo;

    @Schema(example = "Prime Rib ao ponto")
    private String descricao;

    @Schema(example = "image/jpeg")
    private String contentType;

    @Schema(description = "Tamanho da imagem em bytes", example = "60552")
    private Long tamanho;
}
