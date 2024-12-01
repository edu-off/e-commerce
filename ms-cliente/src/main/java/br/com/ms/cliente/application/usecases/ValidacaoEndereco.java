package br.com.ms.cliente.application.usecases;

import br.com.ms.cliente.application.dto.EnderecoDTO;
import br.com.ms.cliente.application.exceptions.EnderecoException;
import br.com.ms.cliente.domain.entities.ClienteEntity;
import br.com.ms.cliente.domain.entities.EnderecoEntity;
import org.springframework.stereotype.Service;

@Service
public class ValidacaoEndereco {

    public EnderecoEntity execute(EnderecoDTO enderecoDTO, ClienteEntity clienteEntity) {
        EnderecoEntity enderecoEntity = null;
        try {
            enderecoEntity = new EnderecoEntity(enderecoDTO.getLogradouro(),
                    enderecoDTO.getBairro(),
                    enderecoDTO.getCidade(),
                    enderecoDTO.getUf(),
                    enderecoDTO.getCep(),
                    clienteEntity);
        } catch(IllegalArgumentException exception) {
            throw new EnderecoException("erro ao validar endereço: " + exception.getMessage());
        }
        return enderecoEntity;
    }
}
