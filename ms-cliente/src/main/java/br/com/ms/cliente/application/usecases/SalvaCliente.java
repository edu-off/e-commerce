package br.com.ms.cliente.application.usecases;

import br.com.ms.cliente.application.gateways.ClienteGateway;
import br.com.ms.cliente.domain.entities.ClienteEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalvaCliente {

    private final ClienteGateway clienteGateway;

    @Autowired
    public SalvaCliente(ClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }

    public ClienteEntity execute(ClienteEntity clienteEntity) {
        return clienteGateway.salvarCliente(clienteEntity);
    }

}
