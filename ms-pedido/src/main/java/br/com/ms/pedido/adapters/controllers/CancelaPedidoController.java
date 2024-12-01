package br.com.ms.pedido.adapters.controllers;

import br.com.ms.pedido.application.exceptions.PedidoException;
import br.com.ms.pedido.application.usecases.*;
import br.com.ms.pedido.domain.entities.PedidoEntity;
import br.com.ms.pedido.domain.enums.StatusPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CancelaPedidoController {

    private final BuscaPedido buscaPedido;
    private final CancelaPedido cancelaPedido;
    private final IncrementaEstoque incrementaEstoque;

    @Autowired
    public CancelaPedidoController(BuscaPedido buscaPedido,
                                   CancelaPedido cancelaPedido,
                                   IncrementaEstoque incrementaEstoque) {
        this.buscaPedido = buscaPedido;
        this.cancelaPedido = cancelaPedido;
        this.incrementaEstoque = incrementaEstoque;
    }

    public void run(Long id) {
        PedidoEntity pedidoEntity = buscaPedido.run(id);
        if (pedidoEntity.getStatus().equals(StatusPedido.CANCELADO))
            throw new PedidoException("pedido já está cancelado");
        if (pedidoEntity.getStatus().equals(StatusPedido.CONCLUIDO))
            throw new PedidoException("pedido já está concluído");
        if (pedidoEntity.getStatus().equals(StatusPedido.CONFIRMADO))
            incrementaEstoque.run(pedidoEntity);
        cancelaPedido.run(id, pedidoEntity);
    }

}
