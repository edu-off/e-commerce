package br.com.ms.cliente.application.gateways;

import br.com.ms.cliente.domain.entities.EnderecoEntity;
import org.springframework.stereotype.Component;

@Component
public interface EnderecoGateway {

    EnderecoEntity salvarEndereco(EnderecoEntity enderecoEntity);
    EnderecoEntity atualizarEndereco(Long id, EnderecoEntity enderecoEntity);
    EnderecoEntity buscaEnderecoPorCliente(Long clienteId);

}
