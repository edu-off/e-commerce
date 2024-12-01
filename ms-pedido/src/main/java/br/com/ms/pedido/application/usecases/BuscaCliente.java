package br.com.ms.pedido.application.usecases;

import br.com.ms.pedido.application.dto.ClienteDTO;
import br.com.ms.pedido.application.gateways.ClienteGateway;
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
        ClienteDTO clienteDTO = clienteGateway.buscaPorId(id);
        if (Objects.isNull(clienteDTO))
            throw new NoSuchElementException("cliente não encontrado");
    }

}
