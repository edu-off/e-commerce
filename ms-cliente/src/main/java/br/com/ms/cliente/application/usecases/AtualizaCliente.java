package br.com.ms.cliente.application.usecases;

import br.com.ms.cliente.application.gateways.ClienteGateway;
import br.com.ms.cliente.domain.entities.ClienteEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AtualizaCliente {

    private final ClienteGateway clienteGateway;

    @Autowired
    public AtualizaCliente(ClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }

    public ClienteEntity execute(Long id, ClienteEntity clienteEntity) {
        return clienteGateway.atualizarCliente(id, clienteEntity);
    }

}
