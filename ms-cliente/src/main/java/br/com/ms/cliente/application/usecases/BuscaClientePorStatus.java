package br.com.ms.cliente.application.usecases;

import br.com.ms.cliente.application.gateways.ClienteGateway;
import br.com.ms.cliente.domain.entities.ClienteEntity;
import br.com.ms.cliente.domain.enums.StatusCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BuscaClientePorStatus {

    private final ClienteGateway clienteGateway;

    @Autowired
    public BuscaClientePorStatus(ClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }

    public Page<ClienteEntity> execute(String status, Pageable pageable) {
        StatusCliente statusCliente = StatusCliente.get(status);
        return clienteGateway.buscaClientePorStatus(statusCliente, pageable);
    }

}
