package br.com.ms.pedido.adapters.controllers;

import br.com.ms.pedido.adapters.presenters.PedidoPresenter;
import br.com.ms.pedido.application.dto.PedidoDTO;
import br.com.ms.pedido.application.dto.ProdutoDTO;
import br.com.ms.pedido.application.exceptions.PedidoException;
import br.com.ms.pedido.application.usecases.*;
import br.com.ms.pedido.domain.entities.PedidoEntity;
import br.com.ms.pedido.domain.enums.StatusPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AtualizaListaProdutosController {

    private final BuscaPedido buscaPedido;
    private final BuscaProduto buscaProduto;
    private final ConsultaEstoque consultaEstoque;
    private final AtualizaPrecoPedido atualizaPrecoPedido;
    private final AtualizaListaProdutos atualizaListaProdutos;
    private final AtualizaPedido atualizaPedido;
    private final PedidoPresenter presenter;

    @Autowired
    public AtualizaListaProdutosController(BuscaPedido buscaPedido,
                                           BuscaProduto buscaProduto,
                                           ConsultaEstoque consultaEstoque,
                                           AtualizaPrecoPedido atualizaPrecoPedido,
                                           AtualizaListaProdutos atualizaListaProdutos,
                                           AtualizaPedido atualizaPedido,
                                           PedidoPresenter presenter) {
        this.buscaPedido = buscaPedido;
        this.buscaProduto = buscaProduto;
        this.consultaEstoque = consultaEstoque;
        this.atualizaPrecoPedido = atualizaPrecoPedido;
        this.atualizaListaProdutos = atualizaListaProdutos;
        this.atualizaPedido = atualizaPedido;
        this.presenter = presenter;
    }

    public PedidoDTO run(Long id, List<ProdutoDTO> produtos) {
        PedidoEntity pedidoEntity = buscaPedido.run(id);
        if (!pedidoEntity.getStatus().equals(StatusPedido.EM_ABERTO))
            throw new PedidoException("pedidos que não estão em aberto não podem sofrer atualizações");

        pedidoEntity.getProdutos().forEach((produtoId, quantidade) -> {
            ProdutoDTO produtoDTO = buscaProduto.run(produtoId);
            consultaEstoque.run(produtoDTO.getQuantidade(), quantidade);
            Double precoProduto = atualizaPrecoPedido.run(quantidade, produtoDTO.getPreco());
            pedidoEntity.setPreco(pedidoEntity.getPreco() + precoProduto);
        });

        PedidoEntity pedidoEntityWithNewProdutos = atualizaListaProdutos.run(pedidoEntity, produtos);
        PedidoEntity pedidoEntityUpdated = atualizaPedido.run(id, pedidoEntityWithNewProdutos);
        return presenter.transform(pedidoEntityUpdated);
    }

}
