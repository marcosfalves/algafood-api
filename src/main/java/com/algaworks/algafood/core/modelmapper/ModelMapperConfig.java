package com.algaworks.algafood.core.modelmapper;

import com.algaworks.algafood.api.model.CidadeResumoModel;
import com.algaworks.algafood.api.model.EnderecoModel;
import com.algaworks.algafood.domain.model.Cidade;
import org.modelmapper.ModelMapper;
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
}
