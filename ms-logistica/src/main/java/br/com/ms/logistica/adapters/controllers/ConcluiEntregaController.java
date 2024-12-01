package br.com.ms.logistica.adapters.controllers;

import br.com.ms.logistica.application.exceptions.EntregaException;
import br.com.ms.logistica.application.usecases.*;
import br.com.ms.logistica.domain.entities.EntregaEntity;
import br.com.ms.logistica.domain.entities.EntregadorEntity;
import br.com.ms.logistica.domain.enums.StatusEntrega;
import br.com.ms.logistica.domain.enums.StatusEntregador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Controller
public class ConcluiEntregaController {

    private final BuscaEntrega buscaEntrega;
    private final AtualizaEntrega atualizaEntrega;
    private final ConcluiPedido concluiPedido;
    private final AtualizaEntregador atualizaEntregador;

    @Autowired
    public ConcluiEntregaController(BuscaEntrega buscaEntrega,
                                    AtualizaEntrega atualizaEntrega,
                                    ConcluiPedido concluiPedido,
                                    AtualizaEntregador atualizaEntregador) {
        this.buscaEntrega = buscaEntrega;
        this.atualizaEntrega = atualizaEntrega;
        this.concluiPedido = concluiPedido;
        this.atualizaEntregador = atualizaEntregador;
    }

    public void run(Long id) {
        EntregaEntity entregaEntity = buscaEntrega.run(id);
        if (entregaEntity.getStatus().equals(StatusEntrega.CANCELADA))
            throw new EntregaException("entrega cancelada");
        if (entregaEntity.getStatus().equals(StatusEntrega.CONCLUIDA))
            throw new EntregaException("entrega já está concluída");
        if (entregaEntity.getStatus().equals(StatusEntrega.PENDENTE))
            throw new EntregaException("entregas pendentes não podem ser concluidas");
        entregaEntity.setStatus(StatusEntrega.CONCLUIDA);
        atualizaEntrega.run(id, entregaEntity);
        concluiPedido.run(entregaEntity.getPedidoId());
        if (Objects.isNull(entregaEntity.getEntregador()))
            throw new NoSuchElementException("entregador não encontrado");
        EntregadorEntity entregadorEntity = entregaEntity.getEntregador();
        entregadorEntity.setStatus(StatusEntregador.DISPONIVEL);
        atualizaEntregador.run(entregadorEntity.getId(), entregadorEntity);
    }

}
