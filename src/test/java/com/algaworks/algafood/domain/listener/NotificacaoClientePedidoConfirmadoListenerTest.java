package com.algaworks.algafood.domain.listener;

import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.algaworks.algafood.helper.PedidoHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

class NotificacaoClientePedidoConfirmadoListenerTest {

    @Mock
    private EnvioEmailService envioEmailService;

    @InjectMocks
    private NotificacaoClientePedidoConfirmadoListener listener;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveExecutarEnvioEmail_QuandoReceberEventoPedidoConfirmado() {
        var event = pedidoConfirmadoEventBuilder();

        listener.aoConfirmarPedido(event);

        verify(envioEmailService).enviar(ArgumentMatchers.any());
    }

    private PedidoConfirmadoEvent pedidoConfirmadoEventBuilder() {
        Pedido pedido = PedidoHelper.builder();
        pedido.confirmar();

        return new PedidoConfirmadoEvent(pedido);
    }

}