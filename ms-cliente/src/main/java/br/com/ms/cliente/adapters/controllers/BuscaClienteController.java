package br.com.ms.cliente.adapters.controllers;

import br.com.ms.cliente.adapters.presenters.ClientePresenter;
import br.com.ms.cliente.application.dto.ClienteDTO;
import br.com.ms.cliente.application.usecases.*;
import br.com.ms.cliente.domain.entities.ClienteEntity;
import br.com.ms.cliente.domain.entities.EnderecoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

@Controller
public class BuscaClienteController {

    private final BuscaCliente buscaCliente;
    private final BuscaClientePorEmail buscaClientePorEmail;
    private final BuscaClientePorNome buscaClientePorNome;
    private final BuscaClientePorStatus buscaClientePorStatus;
    private final BuscaEndereco buscaEndereco;
    private final ClientePresenter presenter;

    @Autowired
    public BuscaClienteController(BuscaCliente buscaCliente,
                                  BuscaClientePorEmail buscaClientePorEmail,
                                  BuscaClientePorNome buscaClientePorNome,
                                  BuscaClientePorStatus buscaClientePorStatus,
                                  BuscaEndereco buscaEndereco,
                                  ClientePresenter presenter) {
        this.buscaCliente = buscaCliente;
        this.buscaClientePorEmail = buscaClientePorEmail;
        this.buscaClientePorNome = buscaClientePorNome;
        this.buscaClientePorStatus = buscaClientePorStatus;
        this.buscaEndereco = buscaEndereco;
        this.presenter = presenter;
    }

    public ClienteDTO porId(Long id) {
        ClienteEntity clienteEntity = buscaCliente.execute(id);
        EnderecoEntity enderecoEntity = buscaEndereco.execute(id);
        return presenter.transform(clienteEntity, enderecoEntity);
    }

    public Page<ClienteDTO> porEmail(String email, Pageable pageable) {
        Page<ClienteEntity> clientes = buscaClientePorEmail.execute(email, pageable);
        return presenter.transform(clientes, pageable);
    }

    public Page<ClienteDTO> porNome(String nome, Pageable pageable) {
        Page<ClienteEntity> clientes = buscaClientePorNome.execute(nome, pageable);
        return presenter.transform(clientes, pageable);
    }

    public Page<ClienteDTO> porStatus(String status, Pageable pageable) {
        Page<ClienteEntity> clientes = buscaClientePorStatus.execute(status, pageable);
        return presenter.transform(clientes, pageable);
    }

}
