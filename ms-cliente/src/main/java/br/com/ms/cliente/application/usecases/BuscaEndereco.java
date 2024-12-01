package br.com.ms.cliente.application.usecases;

import br.com.ms.cliente.application.gateways.EnderecoGateway;
import br.com.ms.cliente.domain.entities.EnderecoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuscaEndereco {

    private final EnderecoGateway enderecoGateway;

    @Autowired
    public BuscaEndereco(EnderecoGateway enderecoGateway) {
        this.enderecoGateway = enderecoGateway;
    }

    public EnderecoEntity execute(Long clienteId) {
        return enderecoGateway.buscaEnderecoPorCliente(clienteId);
    }

}
