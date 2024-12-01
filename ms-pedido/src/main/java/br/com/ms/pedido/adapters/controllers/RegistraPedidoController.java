package br.com.ms.pedido.adapters.controllers;

import br.com.ms.pedido.adapters.presenters.PedidoPresenter;
import br.com.ms.pedido.application.dto.PedidoDTO;
import br.com.ms.pedido.application.dto.ProdutoDTO;
import br.com.ms.pedido.application.usecases.*;
import br.com.ms.pedido.domain.entities.PedidoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class RegistraPedidoController {

    private final ValidaPedido validaPedido;
    private final BuscaCliente buscaCliente;
    private final BuscaProduto buscaProduto;
    private final ConsultaEstoque consultaEstoque;
    private final AtualizaPrecoPedido atualizaPrecoPedido;
    private final SalvaPedido salvaPedido;
    private final PedidoPresenter presenter;

    @Autowired
    public RegistraPedidoController(ValidaPedido validaPedido,
                                    BuscaCliente buscaCliente,
                                    BuscaProduto buscaProduto,
                                    ConsultaEstoque consultaEstoque,
                                    AtualizaPrecoPedido atualizaPrecoPedido,
                                    SalvaPedido salvaPedido,
                                    PedidoPresenter presenter) {
        this.validaPedido = validaPedido;
        this.buscaCliente = buscaCliente;
        this.buscaProduto = buscaProduto;
        this.consultaEstoque = consultaEstoque;
        this.atualizaPrecoPedido = atualizaPrecoPedido;
        this.salvaPedido = salvaPedido;
        this.presenter = presenter;
    }

    public PedidoDTO run(PedidoDTO pedidoDTO) {
        PedidoEntity pedidoEntity = validaPedido.run(pedidoDTO);
        buscaCliente.run(pedidoEntity.getClienteId());
        pedidoEntity.getProdutos().forEach((produtoId, quantidade) -> {
            ProdutoDTO produtoDTO = buscaProduto.run(produtoId);
            consultaEstoque.run(produtoDTO.getQuantidade(), quantidade);
            Double precoProduto = atualizaPrecoPedido.run(quantidade, produtoDTO.getPreco());
            pedidoEntity.setPreco(pedidoEntity.getPreco() + precoProduto);
        });
        PedidoEntity pedidoEntityCreated = salvaPedido.run(pedidoEntity);
        return presenter.transform(pedidoEntityCreated);
    }

}
