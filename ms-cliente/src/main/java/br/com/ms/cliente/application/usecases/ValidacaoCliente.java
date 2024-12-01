package br.com.ms.cliente.application.usecases;

import br.com.ms.cliente.application.dto.ClienteDTO;
import br.com.ms.cliente.application.exceptions.ClienteException;
import br.com.ms.cliente.domain.entities.ClienteEntity;
import br.com.ms.cliente.domain.enums.StatusCliente;
import org.springframework.stereotype.Service;

@Service
public class ValidacaoCliente {

    public ClienteEntity execute(ClienteDTO clienteDTO) {
        ClienteEntity clienteEntity = null;
        try {
            clienteEntity = new ClienteEntity(clienteDTO.getNome(),
                    StatusCliente.get(clienteDTO.getStatus()),
                    clienteDTO.getEmail(),
                    clienteDTO.getDdd(),
                    clienteDTO.getTelefone());
        } catch(IllegalArgumentException exception) {
            throw new ClienteException("erro ao validar cliente: " + exception.getMessage());
        }
        return clienteEntity;
    }

}
