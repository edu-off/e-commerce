package br.com.ms.cliente.adapters.controllers;

import br.com.ms.cliente.application.usecases.AtualizaCliente;
import br.com.ms.cliente.application.usecases.BuscaCliente;
import br.com.ms.cliente.domain.entities.ClienteEntity;
import br.com.ms.cliente.domain.enums.StatusCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.NoSuchElementException;
import java.util.Objects;

@Controller
public class AtualizacaoStatusClienteController {

    private final BuscaCliente buscaCliente;
    private final AtualizaCliente atualizaCliente;

    @Autowired
    public AtualizacaoStatusClienteController(BuscaCliente buscaCliente,
                             AtualizaCliente atualizaCliente) {
        this.buscaCliente = buscaCliente;
        this.atualizaCliente = atualizaCliente;
    }

    public void execute(Long id, StatusCliente status) {
        ClienteEntity clienteEntity = buscaCliente.execute(id);
        if (Objects.isNull(clienteEntity))
            throw new NoSuchElementException("cliente não encontrado");

        clienteEntity.setStatus(status);
        atualizaCliente.execute(id, clienteEntity);
    }

}
