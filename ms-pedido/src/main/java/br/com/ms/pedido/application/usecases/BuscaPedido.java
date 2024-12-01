package br.com.ms.pedido.application.usecases;

import br.com.ms.pedido.application.gateways.PedidoGateway;
import br.com.ms.pedido.domain.entities.PedidoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuscaPedido {

    private final PedidoGateway pedidoGateway;

    @Autowired
    public BuscaPedido(PedidoGateway pedidoGateway) {
        this.pedidoGateway = pedidoGateway;
    }

    public PedidoEntity run(Long id) {
        return pedidoGateway.buscarPorId(id);
    }

}
