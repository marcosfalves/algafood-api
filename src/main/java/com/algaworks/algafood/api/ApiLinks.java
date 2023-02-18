package com.algaworks.algafood.api;

import com.algaworks.algafood.api.controller.CidadeController;
import com.algaworks.algafood.api.controller.CozinhaController;
import com.algaworks.algafood.api.controller.EstadoController;
import com.algaworks.algafood.api.controller.FluxoPedidoController;
import com.algaworks.algafood.api.controller.FormaPagamentoController;
import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.controller.RestauranteFormaPagamentoController;
import com.algaworks.algafood.api.controller.RestauranteProdutoController;
import com.algaworks.algafood.api.controller.RestauranteUsuarioResponsavelController;
import com.algaworks.algafood.api.controller.UsuarioController;
import com.algaworks.algafood.api.controller.UsuarioGrupoController;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ApiLinks {

    public static final TemplateVariables PAGINACAO_VARIABLES = new TemplateVariables(
            new TemplateVariable("page", VariableType.REQUEST_PARAM),
            new TemplateVariable("size", VariableType.REQUEST_PARAM),
            new TemplateVariable("sort", VariableType.REQUEST_PARAM)
    );

    public static final TemplateVariables PROJECAO_VARIABLES = new TemplateVariables(
            new TemplateVariable("projecao", VariableType.REQUEST_PARAM)
    );

    public Link linkToPedidos(String relation) {
        var filtroVariables = new TemplateVariables(
                new TemplateVariable("clienteId", VariableType.REQUEST_PARAM),
                new TemplateVariable("restauranteId", VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoInicio", VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoFim", VariableType.REQUEST_PARAM)
        );

        String pedidosUrl = linkTo(PedidoController.class).toUri().toString();

        return Link.of(UriTemplate.of(pedidosUrl,
                PAGINACAO_VARIABLES.concat(filtroVariables)), relation);
    }

    public Link linkToConfirmacaoPedido(String codigoPedido, String relation) {
        return linkTo(
                methodOn(FluxoPedidoController.class).confirmar(codigoPedido)
        ).withRel(relation);
    }

    public Link linkToCancelamentoPedido(String codigoPedido, String relation) {
        return linkTo(
                methodOn(FluxoPedidoController.class).cancelar(codigoPedido)
        ).withRel(relation);
    }

    public Link linkToEntregaPedido(String codigoPedido, String relation) {
        return linkTo(
                methodOn(FluxoPedidoController.class).entregar(codigoPedido)
        ).withRel(relation);
    }

    public Link linkToRestaurante(Long restauranteId, String relation) {
        return linkTo(
                methodOn(RestauranteController.class).buscar(restauranteId)
        ).withRel(relation);
    }

    public Link linkToRestaurante(Long restauranteId) {
        return linkToRestaurante(restauranteId, IanaLinkRelations.SELF.value());
    }

    public Link linkToRestaurantes(String relation) {
        String restaurantesUrl = linkTo(RestauranteController.class).toUri().toString();

        return Link.of(UriTemplate.of(restaurantesUrl, PROJECAO_VARIABLES), relation);
    }

    public Link linkToRestaurantes() {
        return linkToRestaurantes(IanaLinkRelations.SELF.value());
    }

    public Link linkToRestauranteAbertura(Long restauranteId, String relation) {
        return linkTo(
                methodOn(RestauranteController.class).abrir(restauranteId)
        ).withRel(relation);
    }

    public Link linkToRestauranteFechamento(Long restauranteId, String relation) {
        return linkTo(
                methodOn(RestauranteController.class).fechar(restauranteId)
        ).withRel(relation);
    }

    public Link linkToRestauranteInativacao(Long restauranteId, String relation) {
        return linkTo(
                methodOn(RestauranteController.class).inativar(restauranteId)
        ).withRel(relation);
    }

    public Link linkToRestauranteAtivacao(Long restauranteId, String relation) {
        return linkTo(
                methodOn(RestauranteController.class).ativar(restauranteId)
        ).withRel(relation);
    }

    public Link linkToUsuario(Long usuarioId, String relation) {
        return linkTo(
                methodOn(UsuarioController.class).buscar(usuarioId)
        ).withRel(relation);
    }

    public Link linkToUsuario(Long usuarioId) {
        return linkToUsuario(usuarioId, IanaLinkRelations.SELF.value());
    }

    public Link linkToUsuarios(String relation) {
        return linkTo(UsuarioController.class).withRel(relation);
    }

    public Link linkToUsuarios() {
        return linkToUsuarios(IanaLinkRelations.SELF.value());
    }

    public Link linkToGruposUsuario(Long usuarioId, String relation) {
        return linkTo(
                methodOn(UsuarioGrupoController.class).listar(usuarioId)
        ).withRel(relation);
    }

    public Link linkToGruposUsuario(Long usuarioId) {
        return linkToGruposUsuario(usuarioId, IanaLinkRelations.SELF.value());
    }

    public Link linkToRestauranteFormasPagamento(Long restauranteId) {
        return linkToRestauranteFormasPagamento(restauranteId, IanaLinkRelations.SELF.value());
    }

    public Link linkToRestauranteFormasPagamento(Long restauranteId, String relation) {
        return linkTo(
                methodOn(RestauranteFormaPagamentoController.class).listar(restauranteId)
        ).withRel(relation);
    }

    public Link linkToRestauranteFormaPagamentoDesassociacao(
            Long restauranteId, Long formaPagamentoId, String relation) {
        return linkTo(
                methodOn(RestauranteFormaPagamentoController.class).desassociarFormaPagamento(restauranteId, formaPagamentoId)
        ).withRel(relation);
    }

    public Link linkToRestauranteFormaPagamentoAssociacao(Long restauranteId, String relation) {
        return linkTo(
                methodOn(RestauranteFormaPagamentoController.class).associarFormaPagamento(restauranteId, null)
        ).withRel(relation);
    }

    public Link linkToRestauranteResponsaveis(Long restauranteId, String relation) {
        return linkTo(
                methodOn(RestauranteUsuarioResponsavelController.class).listar(restauranteId)
        ).withRel(relation);
    }

    public Link linkToRestauranteResponsaveis(Long restauranteId) {
        return linkToRestauranteResponsaveis(restauranteId, IanaLinkRelations.SELF.value());
    }

    public Link linkToRestauranteResponsavelDesassociacao(Long restauranteId, Long usuarioId, String relation) {
        return linkTo(
                methodOn(RestauranteUsuarioResponsavelController.class).desassociarUsuario(restauranteId, usuarioId)
        ).withRel(relation);
    }

    public Link linkToRestauranteResponsavelAssociacao(Long restauranteId, String relation) {
        return linkTo(
                methodOn(RestauranteUsuarioResponsavelController.class).associarUsuario(restauranteId, null)
        ).withRel(relation);
    }

    public Link linkToFormaPagamento(Long formaPagamentoId, String relation) {
        return linkTo(
                methodOn(FormaPagamentoController.class).buscar(formaPagamentoId, null)
        ).withRel(relation);
    }

    public Link linkToFormaPagamento(Long formaPagamentoId) {
        return linkToFormaPagamento(formaPagamentoId, IanaLinkRelations.SELF.value());
    }

    public Link linkToFormasPagamento(String relation) {
        return linkTo(
                methodOn(FormaPagamentoController.class).listar(null)
        ).withRel(relation);
    }

    public Link linkToFormasPagamento() {
        return linkToFormasPagamento(IanaLinkRelations.SELF.value());
    }

    public Link linkToCidade(Long cidadeId, String relation) {
        return linkTo(
                methodOn(CidadeController.class).buscar(cidadeId)
        ).withRel(relation);
    }

    public Link linkToCidade(Long cidadeId) {
        return linkToCidade(cidadeId, IanaLinkRelations.SELF.value());
    }

    public Link linkToCidades(String relation) {
        return linkTo(methodOn(CidadeController.class).listar())
                .withRel(relation);
    }

    public Link linkToCidades() {
        return linkToCidades(IanaLinkRelations.SELF.value());
    }

    public Link linkToEstado(Long estadoId, String relation) {
        return linkTo(
                methodOn(EstadoController.class).buscar(estadoId)
        ).withRel(relation);
    }

    public Link linkToEstado(Long estadoId) {
        return linkToEstado(estadoId, IanaLinkRelations.SELF.value());
    }

    public Link linkToEstados(String relation) {
        return linkTo(methodOn(EstadoController.class).listar())
                .withRel(relation);
    }

    public Link linkToEstados() {
        return linkToEstados(IanaLinkRelations.SELF.value());
    }

    public Link linkToProduto(Long restauranteId, Long produtoId, String relation) {
        return linkTo(
                methodOn(RestauranteProdutoController.class).buscar(restauranteId, produtoId)
        ).withRel(relation);
    }

    public Link linkToProduto(Long restauranteId, Long produtoId) {
        return linkToProduto(restauranteId, produtoId, IanaLinkRelations.SELF.value());
    }

    public Link linkToCozinha(Long cozinhaId, String relation) {
        return linkTo(
                methodOn(CozinhaController.class).buscar(cozinhaId)
        ).withRel(relation);
    }

    public Link linkToCozinha(Long cozinhaId) {
        return linkToCozinha(cozinhaId, IanaLinkRelations.SELF.value());
    }

    public Link linkToCozinhas(String relation) {
        return linkTo(CozinhaController.class)
                .withRel(relation);
    }

    public Link linkToCozinhas() {
        return linkToCozinhas(IanaLinkRelations.SELF.value());
    }
}
