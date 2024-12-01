package br.com.ms.cliente.application.usecases;

import br.com.ms.cliente.application.gateways.EnderecoGateway;
import br.com.ms.cliente.domain.entities.ClienteEntity;
import br.com.ms.cliente.domain.entities.EnderecoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalvaEndereco {

    private final EnderecoGateway enderecoGateway;

    @Autowired
    public SalvaEndereco(EnderecoGateway enderecoGateway) {
        this.enderecoGateway = enderecoGateway;
    }

    public EnderecoEntity execute(EnderecoEntity enderecoEntity) {
        return enderecoGateway.salvarEndereco(enderecoEntity);
    }

}
