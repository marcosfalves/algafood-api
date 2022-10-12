package com.algaworks.algafood.jpa;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.repository.PermissaoRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class DesafioMapeamentoMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        FormaPagamentoRepository formaPagamentoRepository = applicationContext.getBean(FormaPagamentoRepository.class);
        PermissaoRepository permissaoRepository = applicationContext.getBean(PermissaoRepository.class);
        EstadoRepository estadoRepository = applicationContext.getBean(EstadoRepository.class);
        CidadeRepository cidadeRepository = applicationContext.getBean(CidadeRepository.class);


        System.out.println("Listando todos:");
        List<FormaPagamento> formasPagamento = formaPagamentoRepository.listar();
        formasPagamento.forEach(System.out::println);

        List<Permissao> permissoes = permissaoRepository.listar();
        permissoes.forEach(System.out::println);

        List<Estado> estados = estadoRepository.listar();
        estados.forEach(System.out::println);

        List<Cidade> cidades = cidadeRepository.listar();
        cidades.forEach(System.out::println);
    }
}
