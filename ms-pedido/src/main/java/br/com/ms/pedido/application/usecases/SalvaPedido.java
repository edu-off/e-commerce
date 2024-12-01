package br.com.ms.pedido.application.usecases;

import br.com.ms.pedido.application.gateways.PedidoGateway;
import br.com.ms.pedido.domain.entities.PedidoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalvaPedido {

    private final PedidoGateway pedidoGateway;

    @Autowired
    public SalvaPedido(PedidoGateway pedidoGateway) {
        this.pedidoGateway = pedidoGateway;
    }

    public PedidoEntity run(PedidoEntity pedidoEntity) {
        return pedidoGateway.salvar(pedidoEntity);
    }

}
