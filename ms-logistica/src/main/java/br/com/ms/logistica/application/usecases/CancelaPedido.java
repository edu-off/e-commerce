package br.com.ms.logistica.application.usecases;

import br.com.ms.logistica.application.gateways.PedidoGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CancelaPedido {

    private final PedidoGateway pedidoGateway;

    @Autowired
    public CancelaPedido(PedidoGateway pedidoGateway) {
        this.pedidoGateway = pedidoGateway;
    }

    public void run(Long pedidoId) {
        pedidoGateway.cancelaPedido(pedidoId);
    }

}
