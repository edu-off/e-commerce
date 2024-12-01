package br.com.ms.pedido.adapters.gateways;

import br.com.ms.pedido.adapters.clients.EntregaFeignClient;
import br.com.ms.pedido.application.dto.EntregaDTO;
import br.com.ms.pedido.application.gateways.EntregaGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntregaGatewayImpl implements EntregaGateway {

    private final EntregaFeignClient client;

    @Autowired
    public EntregaGatewayImpl(EntregaFeignClient client) {
        this.client = client;
    }

    @Override
    public EntregaDTO registraEntrega(EntregaDTO entregaDTO) {
        return client.registraEntrega(entregaDTO);
    }

}
