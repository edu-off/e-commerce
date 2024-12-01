package br.com.ms.cliente.application.usecases;

import br.com.ms.cliente.application.gateways.ClienteGateway;
import br.com.ms.cliente.domain.entities.ClienteEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuscaCliente {

    private final ClienteGateway clienteGateway;

    @Autowired
    public BuscaCliente(ClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }

    public ClienteEntity execute(Long id) {
        return clienteGateway.buscaCliente(id);
    }

}
