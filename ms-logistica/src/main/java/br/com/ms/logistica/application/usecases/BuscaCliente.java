package br.com.ms.logistica.application.usecases;

import br.com.ms.logistica.application.gateways.ClienteGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class BuscaCliente {

    private final ClienteGateway clienteGateway;

    @Autowired
    public BuscaCliente(ClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }

    public void run(Long id) {
        if (Objects.isNull(clienteGateway.buscaPorId(id)))
            throw new NoSuchElementException("cliente não encontrado");
    }

}
