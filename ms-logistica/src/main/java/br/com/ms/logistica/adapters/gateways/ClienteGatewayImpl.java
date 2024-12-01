package br.com.ms.logistica.adapters.gateways;

import br.com.ms.logistica.adapters.clients.ClienteFeignClient;
import br.com.ms.logistica.application.dto.ClienteDTO;
import br.com.ms.logistica.application.gateways.ClienteGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClienteGatewayImpl implements ClienteGateway {

    private final ClienteFeignClient client;

    @Autowired
    public ClienteGatewayImpl(ClienteFeignClient client) {
        this.client = client;
    }

    @Override
    public ClienteDTO buscaPorId(Long id) {
        return client.buscaPorId(id);
    }

}
