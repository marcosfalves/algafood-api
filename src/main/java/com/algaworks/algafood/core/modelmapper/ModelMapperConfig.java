package com.algaworks.algafood.core.modelmapper;

import com.algaworks.algafood.api.model.CidadeResumoModel;
import com.algaworks.algafood.api.model.input.ItemPedidoInput;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.ItemPedido;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper(){
        var modelMapper = new ModelMapper();

        modelMapper.createTypeMap(Cidade.class, CidadeResumoModel.class)
                .<String>addMapping(
                        cidadeSrc -> cidadeSrc.getEstado().getNome(),
                        (cidadeResumoModelDest, value) -> cidadeResumoModelDest.setEstado(value)
                );

        return modelMapper;
    }

    @Bean
    public ModelMapper modelMapperStrict(){
        var modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
                .<Long>addMapping(
                        itemPedidoInputSrc -> itemPedidoInputSrc.getProdutoId(),
                        (itemPedidoDest, value) -> itemPedidoDest.getProduto().setId(value)
                );

        return modelMapper;
    }
}
