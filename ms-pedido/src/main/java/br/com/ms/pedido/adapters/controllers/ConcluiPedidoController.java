package br.com.ms.pedido.adapters.controllers;

import br.com.ms.pedido.application.exceptions.PedidoException;
import br.com.ms.pedido.application.usecases.BuscaPedido;
import br.com.ms.pedido.application.usecases.ConcluiPedido;
import br.com.ms.pedido.domain.entities.PedidoEntity;
import br.com.ms.pedido.domain.enums.StatusPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ConcluiPedidoController {

    private final BuscaPedido buscaPedido;
    private final ConcluiPedido concluiPedido;

    @Autowired
    public ConcluiPedidoController(BuscaPedido buscaPedido,
                                   ConcluiPedido concluiPedido) {
        this.buscaPedido = buscaPedido;
        this.concluiPedido = concluiPedido;
    }

    public void run(Long id) {
        PedidoEntity pedidoEntity = buscaPedido.run(id);
        if (pedidoEntity.getStatus().equals(StatusPedido.CONCLUIDO))
            throw new PedidoException("pedido já está concluído");
        if (pedidoEntity.getStatus().equals(StatusPedido.CANCELADO))
            throw new PedidoException("pedido cancelado");
        if (pedidoEntity.getStatus().equals(StatusPedido.EM_ABERTO))
            throw new PedidoException("pedido ainda está em aberto");
        concluiPedido.run(id, pedidoEntity);
    }

}
