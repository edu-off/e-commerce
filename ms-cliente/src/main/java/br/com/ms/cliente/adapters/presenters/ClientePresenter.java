package br.com.ms.cliente.adapters.presenters;

import br.com.ms.cliente.application.dto.ClienteDTO;
import br.com.ms.cliente.application.dto.EnderecoDTO;
import br.com.ms.cliente.domain.entities.ClienteEntity;
import br.com.ms.cliente.domain.entities.EnderecoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class ClientePresenter {

    public ClienteDTO transform(ClienteEntity clienteEntity, EnderecoEntity enderecoEntity) {
        EnderecoDTO enderecoDTO = new EnderecoDTO(enderecoEntity.getId(),
                enderecoEntity.getLogradouro(),
                enderecoEntity.getBairro(),
                enderecoEntity.getCidade(), enderecoEntity.getUf(),
                enderecoEntity.getCep());
        return new ClienteDTO(clienteEntity.getId(),
                clienteEntity.getNome(),
                clienteEntity.getStatus().toString(),
                clienteEntity.getEmail(),
                clienteEntity.getDdd(),
                clienteEntity.getTelefone(),
                enderecoDTO);
    }

    public Page<ClienteDTO> transform(Page<ClienteEntity> clientes, Pageable pageable) {
        List<ClienteDTO> clientesDTO = new ArrayList<>();
        clientes.getContent().forEach(clienteEntity -> {
            clientesDTO.add(new ClienteDTO(clienteEntity.getId(),
                    clienteEntity.getNome(),
                    clienteEntity.getStatus().toString(),
                    clienteEntity.getEmail(),
                    clienteEntity.getDdd(),
                    clienteEntity.getTelefone(),
                    null));
        });

        return PageableExecutionUtils.getPage(clientesDTO, pageable, clientesDTO::size);
    }

}
