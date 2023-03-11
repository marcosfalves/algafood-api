package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.ApiLinks;
import com.algaworks.algafood.core.security.ApiSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping(path = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {

    @Autowired
    private ApiLinks apiLinks;

    @Autowired
    private ApiSecurity apiSecurity;

    @GetMapping
    public RootEntryPointModel root() {
        var rootEntryPointModel = new RootEntryPointModel();

        if (apiSecurity.podeConsultarCozinhas()) {
            rootEntryPointModel.add(apiLinks.linkToCozinhas("cozinhas"));
        }

        if (apiSecurity.podePesquisarPedidos()) {
            rootEntryPointModel.add(apiLinks.linkToPedidos("pedidos"));
        }

        if (apiSecurity.podeConsultarRestaurantes()) {
            rootEntryPointModel.add(apiLinks.linkToRestaurantes("restaurantes"));
        }

        if (apiSecurity.podeConsultarControleDeAcesso()) {
            rootEntryPointModel.add(apiLinks.linkToGrupos("grupos"));
            rootEntryPointModel.add(apiLinks.linkToUsuarios("usuarios"));
            rootEntryPointModel.add(apiLinks.linkToPermissoes("permissoes"));
        }

        if (apiSecurity.podeConsultarFormasPagamento()) {
            rootEntryPointModel.add(apiLinks.linkToFormasPagamento("formas-pagamento"));
        }

        if (apiSecurity.podeConsultarEstados()) {
            rootEntryPointModel.add(apiLinks.linkToEstados("estados"));
        }

        if (apiSecurity.podeConsultarCidades()) {
            rootEntryPointModel.add(apiLinks.linkToCidades("cidades"));
        }

        if (apiSecurity.podeConsultarEstatisticas()) {
            rootEntryPointModel.add(apiLinks.linkToEstatisticas("estatisticas"));
        }

        return rootEntryPointModel;
    }

    private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {
    }
}
