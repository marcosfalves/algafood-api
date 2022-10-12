package com.algaworks.algafood.jpa;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.math.BigDecimal;
import java.util.List;

public class RestauranteMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        RestauranteRepository restauranteRepository = applicationContext.getBean(RestauranteRepository.class);

        System.out.println("Listando todos:");
        List<Restaurante> todosRestaurantes = restauranteRepository.listar();
        todosRestaurantes.forEach(System.out::println);

        System.out.println("Listando somente um:");
        Restaurante restaurante = restauranteRepository.buscar(1L);
        System.out.println(restaurante);

        System.out.println("Adicionando:");
        Restaurante novoRestaurante = new Restaurante();
        novoRestaurante.setNome("Restaurante da Hora");
        novoRestaurante.setTaxaFrete(new BigDecimal("5.00"));
        novoRestaurante = restauranteRepository.salvar(novoRestaurante);
        System.out.printf("%d - %s - %f\n", novoRestaurante.getId(), novoRestaurante.getNome(), novoRestaurante.getTaxaFrete());

        System.out.println("Alterando restaurante de código 1:");
        restaurante = new Restaurante();
        restaurante.setId(1L);
        restaurante.setNome("Churrascaria BR3");
        restaurante.setTaxaFrete(new BigDecimal("6.00"));
        restaurante = restauranteRepository.salvar(restaurante);
        System.out.printf("%d - %s - %f\n", restaurante.getId(), restaurante.getNome(), restaurante.getTaxaFrete());

        System.out.println("Exluindo restaurante de código 2:");
        restaurante = new Restaurante();
        restaurante.setId(2L);
        restauranteRepository.remover(restaurante);
    }
}
