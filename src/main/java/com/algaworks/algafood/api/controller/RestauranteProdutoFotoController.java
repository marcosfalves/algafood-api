package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.input.FotoProdutoInput;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.util.UUID;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId,
                              FotoProdutoInput fotoProdutoInput){

        var arquivo = fotoProdutoInput.getArquivo();
        var nomeArquivo = UUID.randomUUID() + "_" + arquivo.getOriginalFilename();

        var arquivoFoto = Path.of("/home/malves/", nomeArquivo);

        System.out.println(fotoProdutoInput.getDescricao());
        System.out.println(arquivoFoto);
        System.out.println(arquivo.getContentType());

        try {
            arquivo.transferTo(arquivoFoto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
