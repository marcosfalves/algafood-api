package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.PedidoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmissaoPedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private CadastroCidadeService cadastroCidade;

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamento;

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private CadastroUsuarioService cadastroUsuario;

    @Autowired
    private CadastroProdutoService cadastroProduto;

    @Transactional
    public Pedido emitir(Pedido pedido) {
        validarPedido(pedido);
        validarItens(pedido);

        pedido.calcularValorTotal();

        return pedidoRepository.save(pedido);
    }

    private void validarPedido(Pedido pedido) {
        Cidade cidade = cadastroCidade.buscarOuFalhar(pedido.getEnderecoEntrega().getCidade().getId());
        FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(pedido.getFormaPagamento().getId());
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(pedido.getRestaurante().getId());
        Usuario cliente = cadastroUsuario.buscarOuFalhar(pedido.getCliente().getId());

        pedido.getEnderecoEntrega().setCidade(cidade);
        pedido.setFormaPagamento(formaPagamento);
        pedido.setRestaurante(restaurante);
        pedido.setCliente(cliente);

        if (restaurante.naoAceitaFormaPagamento(formaPagamento)) {
            throw new NegocioException(
                    String.format("Forma de pagamento '%s' não é aceita por esse restaurante.", formaPagamento.getDescricao())
            );
        }
    }

    private void validarItens(Pedido pedido) {
        pedido.getItens().forEach(
                item -> {
                    Produto produto = cadastroProduto.buscarOuFalhar(
                            pedido.getRestaurante().getId(), item.getProduto().getId());
                    item.setPedido(pedido);
                    item.setProduto(produto);
                    item.setPrecoUnitario(produto.getPreco());
                }
        );
    }

    public Pedido buscarOuFalhar(Long pedidoId) {
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));
    }
}
