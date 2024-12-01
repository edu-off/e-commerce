package br.com.ms.logistica.application.usecases;

import br.com.ms.logistica.application.gateways.PedidoGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class BuscaPedido {

    private final PedidoGateway pedidoGateway;

    @Autowired
    public BuscaPedido(PedidoGateway pedidoGateway) {
        this.pedidoGateway = pedidoGateway;
    }

    public void run(Long id) {
        if (Objects.isNull(pedidoGateway.buscaPorId(id)))
            throw new NoSuchElementException("pedido não encontrado");
    }

}
