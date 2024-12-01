package br.com.ms.logistica.adapters.gateways;

import br.com.ms.logistica.adapters.clients.PedidoFeignClient;
import br.com.ms.logistica.application.dto.PedidoDTO;
import br.com.ms.logistica.application.gateways.PedidoGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoGatewayImpl implements PedidoGateway {

    private final PedidoFeignClient client;

    @Autowired
    public PedidoGatewayImpl(PedidoFeignClient client) {
        this.client = client;
    }

    @Override
    public PedidoDTO buscaPorId(Long id) {
        return client.buscaPorId(id);
    }

    @Override
    public void concluiPedido(Long id) {
        client.concluiPedido(id);
    }

    @Override
    public void cancelaPedido(Long id) {
        client.cancelaPedido(id);
    }

}
