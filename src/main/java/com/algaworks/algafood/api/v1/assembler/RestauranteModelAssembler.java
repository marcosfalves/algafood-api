package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.ApiLinks;
import com.algaworks.algafood.api.v1.controller.RestauranteController;
import com.algaworks.algafood.api.v1.model.RestauranteModel;
import com.algaworks.algafood.core.security.ApiSecurity;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteModelAssembler
        extends RepresentationModelAssemblerSupport<Restaurante, RestauranteModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiLinks apiLinks;

    @Autowired
    private ApiSecurity apiSecurity;

    public RestauranteModelAssembler() {
        super(RestauranteController.class, RestauranteModel.class);
    }

    public RestauranteModel toModel(Restaurante restaurante) {
        var restauranteModel = createModelWithId(restaurante.getId(), restaurante);
        modelMapper.map(restaurante, restauranteModel);

        if (apiSecurity.podeConsultarRestaurantes()) {
            restauranteModel.add(
                    apiLinks.linkToRestaurantes(IanaLinkRelations.COLLECTION.value()));

            restauranteModel.add(
                    apiLinks.linkToProdutos(restaurante.getId(), "produtos")
            );

            restauranteModel.add(
                    apiLinks.linkToRestauranteFormasPagamento(restaurante.getId(), "formas-pagamento"));
        }


        if (apiSecurity.podeConsultarCozinhas()) {
            restauranteModel.getCozinha().add(
                    apiLinks.linkToCozinha(restaurante.getCozinha().getId()));
        }


        if (apiSecurity.podeConsultarCidades()) {
            if (restauranteModel.getEndereco() != null
                    && restauranteModel.getEndereco().getCidade() != null) {
                restauranteModel.getEndereco().getCidade().add(
                        apiLinks.linkToCidade(restaurante.getEndereco().getCidade().getId()));
            }
        }


        if (apiSecurity.podeGerenciarCadastroRestaurantes()) {
            restauranteModel.add(
                    apiLinks.linkToRestauranteResponsaveis(restaurante.getId(), "responsaveis"));

            if (restaurante.ativacaoPermitida()) {
                restauranteModel.add(
                        apiLinks.linkToRestauranteAtivacao(restaurante.getId(), "ativar"));
            }

            if (restaurante.inativacaoPermitida()) {
                restauranteModel.add(
                        apiLinks.linkToRestauranteInativacao(restaurante.getId(), "inativar"));
            }
        }


        if (apiSecurity.podeGerenciarFuncionamentoRestaurantes(restaurante.getId())) {
            if (restaurante.aberturaPermitida()) {
                restauranteModel.add(
                        apiLinks.linkToRestauranteAbertura(restaurante.getId(), "abrir"));
            }

            if (restaurante.fechamentoPermitido()) {
                restauranteModel.add(
                        apiLinks.linkToRestauranteFechamento(restaurante.getId(), "fechar"));
            }
        }

        return restauranteModel;
    }

    @Override
    public CollectionModel<RestauranteModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
        var collectionModel = super.toCollectionModel(entities);

        if (apiSecurity.podeConsultarRestaurantes()){
            collectionModel.add(apiLinks.linkToRestaurantes());
        }

        return collectionModel;
    }
}
