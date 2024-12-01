package br.com.ms.cliente.application.usecases;

import br.com.ms.cliente.application.gateways.ClienteGateway;
import br.com.ms.cliente.domain.entities.ClienteEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BuscaClientePorNome {

    private final ClienteGateway clienteGateway;

    @Autowired
    public BuscaClientePorNome(ClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }

    public Page<ClienteEntity> execute(String nome, Pageable pageable) {
        return clienteGateway.buscaClientePorNome(nome, pageable);
    }

}
