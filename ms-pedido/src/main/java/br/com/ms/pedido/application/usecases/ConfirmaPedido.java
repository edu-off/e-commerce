package br.com.ms.pedido.application.usecases;

import br.com.ms.pedido.application.gateways.PedidoGateway;
import br.com.ms.pedido.domain.entities.PedidoEntity;
import br.com.ms.pedido.domain.enums.StatusPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ConfirmaPedido {

    private final PedidoGateway pedidoGateway;

    @Autowired
    public ConfirmaPedido(PedidoGateway pedidoGateway) {
        this.pedidoGateway = pedidoGateway;
    }

    public void run(Long id, PedidoEntity pedidoEntity) {
        pedidoEntity.setStatus(StatusPedido.CONFIRMADO);
        pedidoEntity.setDataConfirmacao(LocalDateTime.now());
        pedidoGateway.atualizar(id, pedidoEntity);
    }

}
