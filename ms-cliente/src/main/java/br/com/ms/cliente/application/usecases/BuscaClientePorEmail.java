package br.com.ms.cliente.application.usecases;

import br.com.ms.cliente.application.gateways.ClienteGateway;
import br.com.ms.cliente.domain.entities.ClienteEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BuscaClientePorEmail {

    private final ClienteGateway clienteGateway;

    @Autowired
    public BuscaClientePorEmail(ClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }

    public Page<ClienteEntity> execute(String Email, Pageable pageable) {
        return clienteGateway.buscaClientePorEmail(Email, pageable);
    }

}
