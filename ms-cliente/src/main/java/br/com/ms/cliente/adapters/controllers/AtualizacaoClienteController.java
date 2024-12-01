package br.com.ms.cliente.adapters.controllers;

import br.com.ms.cliente.adapters.presenters.ClientePresenter;
import br.com.ms.cliente.application.dto.ClienteDTO;
import br.com.ms.cliente.application.usecases.*;
import br.com.ms.cliente.domain.entities.ClienteEntity;
import br.com.ms.cliente.domain.entities.EnderecoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.NoSuchElementException;
import java.util.Objects;

@Controller
public class AtualizacaoClienteController {

    private final ValidacaoCliente validacaoCliente;
    private final ValidacaoEndereco validacaoEndereco;
    private final BuscaCliente buscaCliente;
    private final BuscaEndereco buscaEndereco;
    private final AtualizaCliente atualizaCliente;
    private final AtualizaEndereco atualizaEndereco;
    private final ClientePresenter presenter;

    @Autowired
    public AtualizacaoClienteController(ValidacaoCliente validacaoCliente,
                              ValidacaoEndereco validacaoEndereco,
                              BuscaCliente buscaCliente,
                              BuscaEndereco buscaEndereco,
                              AtualizaCliente atualizaCliente,
                              AtualizaEndereco atualizaEndereco,
                              ClientePresenter presenter) {
        this.validacaoCliente = validacaoCliente;
        this.validacaoEndereco = validacaoEndereco;
        this.buscaCliente = buscaCliente;
        this.buscaEndereco = buscaEndereco;
        this.atualizaCliente = atualizaCliente;
        this.atualizaEndereco = atualizaEndereco;
        this.presenter = presenter;
    }

    public ClienteDTO execute(Long id, ClienteDTO clienteDTO) {
        ClienteEntity clienteEntityRetrieved = buscaCliente.execute(id);
        if (Objects.isNull(clienteEntityRetrieved))
            throw new NoSuchElementException("cliente não encontrado");
        EnderecoEntity enderecoEntityRetrieved = buscaEndereco.execute(id);

        ClienteEntity clienteEntity = validacaoCliente.execute(clienteDTO);
        EnderecoEntity enderecoEntity = validacaoEndereco.execute(clienteDTO.getEndereco(), clienteEntity);

        clienteEntity.setId(id);
        enderecoEntity.setId(enderecoEntityRetrieved.getId());

        ClienteEntity clienteEntityUpdated = atualizaCliente.execute(clienteEntity.getId(), clienteEntity);
        EnderecoEntity enderecoEntityUpdated = atualizaEndereco.execute(enderecoEntity.getId(), enderecoEntity);

        return presenter.transform(clienteEntityUpdated, enderecoEntityUpdated);
    }

}
