package br.com.ms.logistica.application.usecases;

import br.com.ms.logistica.application.dto.ClienteDTO;
import br.com.ms.logistica.application.gateways.ClienteGateway;
import br.com.ms.logistica.domain.entities.EntregaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class RecuperaDadosCliente {

    private final ClienteGateway clienteGateway;

    @Autowired
    public RecuperaDadosCliente(ClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }

    public EntregaEntity porId(EntregaEntity entregaEntity) {
        ClienteDTO clienteDTO = clienteGateway.buscaPorId(entregaEntity.getClienteId());
        if (Objects.isNull(clienteDTO))
            throw new NoSuchElementException("cliente não encontrado");
        entregaEntity.setDestinatario(clienteDTO.getNome());
        entregaEntity.setDdd(clienteDTO.getDdd());
        entregaEntity.setTelefone(clienteDTO.getTelefone());
        entregaEntity.setLogradouro(clienteDTO.getEndereco().getLogradouro());
        entregaEntity.setBairro(clienteDTO.getEndereco().getBairro());
        entregaEntity.setCidade(clienteDTO.getEndereco().getCidade());
        entregaEntity.setUf(clienteDTO.getEndereco().getUf());
        entregaEntity.setCep(clienteDTO.getEndereco().getCep());
        return entregaEntity;
    }

}
