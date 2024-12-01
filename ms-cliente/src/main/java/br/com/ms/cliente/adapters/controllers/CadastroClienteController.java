package br.com.ms.cliente.adapters.controllers;

import br.com.ms.cliente.adapters.presenters.ClientePresenter;
import br.com.ms.cliente.application.dto.ClienteDTO;
import br.com.ms.cliente.application.usecases.SalvaCliente;
import br.com.ms.cliente.application.usecases.SalvaEndereco;
import br.com.ms.cliente.application.usecases.ValidacaoCliente;
import br.com.ms.cliente.application.usecases.ValidacaoEndereco;
import br.com.ms.cliente.domain.entities.ClienteEntity;
import br.com.ms.cliente.domain.entities.EnderecoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CadastroClienteController {

    private final ValidacaoCliente validacaoCliente;
    private final ValidacaoEndereco validacaoEndereco;
    private final SalvaCliente salvaCliente;
    private final SalvaEndereco salvaEndereco;
    private final ClientePresenter presenter;

    @Autowired
    public CadastroClienteController(ValidacaoCliente validacaoCliente,
                           ValidacaoEndereco validacaoEndereco,
                           SalvaCliente salvaCliente,
                           SalvaEndereco salvaEndereco,
                           ClientePresenter presenter) {
        this.validacaoCliente = validacaoCliente;
        this.validacaoEndereco = validacaoEndereco;
        this.salvaCliente = salvaCliente;
        this.salvaEndereco = salvaEndereco;
        this.presenter = presenter;
    }

    public ClienteDTO execute(ClienteDTO clienteDTO) {
        ClienteEntity clienteEntity = validacaoCliente.execute(clienteDTO);
        EnderecoEntity enderecoEntity = validacaoEndereco.execute(clienteDTO.getEndereco(), clienteEntity);
        ClienteEntity clienteEntityCreated = salvaCliente.execute(clienteEntity);
        enderecoEntity.getCliente().setId(clienteEntityCreated.getId());
        EnderecoEntity enderecoEntityCreated = salvaEndereco.execute(enderecoEntity);
        return presenter.transform(clienteEntityCreated, enderecoEntityCreated);
    }

}
