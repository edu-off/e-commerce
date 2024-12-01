package br.com.ms.pedido.adapters.controllers;

import br.com.ms.pedido.application.exceptions.PedidoException;
import br.com.ms.pedido.application.usecases.*;
import br.com.ms.pedido.domain.entities.PedidoEntity;
import br.com.ms.pedido.domain.enums.StatusPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ConfirmaPedidoController {

    private final BuscaPedido buscaPedido;
    private final ConfirmaPedido confirmaPedido;
    private final RegistraEntrega registraEntrega;
    private final DecrementaEstoque decrementaEstoque;

    @Autowired
    public ConfirmaPedidoController(BuscaPedido buscaPedido,
                                    ConfirmaPedido confirmaPedido,
                                    RegistraEntrega registraEntrega,
                                    DecrementaEstoque decrementaEstoque) {
        this.buscaPedido = buscaPedido;
        this.confirmaPedido = confirmaPedido;
        this.registraEntrega = registraEntrega;
        this.decrementaEstoque = decrementaEstoque;
    }

    public void run(Long id) {
        PedidoEntity pedidoEntity = buscaPedido.run(id);
        if (pedidoEntity.getStatus().equals(StatusPedido.CONFIRMADO))
            throw new PedidoException("pedido já está confirmado");
        if (pedidoEntity.getStatus().equals(StatusPedido.CANCELADO))
            throw new PedidoException("pedido cancelado");
        if (pedidoEntity.getStatus().equals(StatusPedido.CONCLUIDO))
            throw new PedidoException("pedido concluído");
        confirmaPedido.run(id, pedidoEntity);
        registraEntrega.run(pedidoEntity.getClienteId(), pedidoEntity.getId());
        decrementaEstoque.run(pedidoEntity);
    }

}
