package br.com.ms.cliente.application.usecases;

import br.com.ms.cliente.application.gateways.EnderecoGateway;
import br.com.ms.cliente.domain.entities.EnderecoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AtualizaEndereco {

    private final EnderecoGateway enderecoGateway;

    @Autowired
    public AtualizaEndereco(EnderecoGateway enderecoGateway) {
        this.enderecoGateway = enderecoGateway;
    }

    public EnderecoEntity execute(Long id, EnderecoEntity enderecoEntity) {
        return enderecoGateway.atualizarEndereco(id, enderecoEntity);
    }

}
