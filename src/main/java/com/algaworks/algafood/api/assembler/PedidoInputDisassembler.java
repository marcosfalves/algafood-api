package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.input.PedidoInput;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoInputDisassembler {

    @Autowired
    private ModelMapper modelMapperStrict;

    public Pedido toDomainObject(PedidoInput pedidoInput) {
        return modelMapperStrict.map(pedidoInput, Pedido.class);
    }

    public void copyToDomainObject(PedidoInput pedidoInput, Pedido pedido) {
        modelMapperStrict.map(pedidoInput, pedido);
    }
}
