package br.com.ms.logistica.adapters.controllers;

import br.com.ms.logistica.application.exceptions.EntregaException;
import br.com.ms.logistica.application.exceptions.EntregadorException;
import br.com.ms.logistica.application.usecases.*;
import br.com.ms.logistica.domain.entities.EntregaEntity;
import br.com.ms.logistica.domain.entities.EntregadorEntity;
import br.com.ms.logistica.domain.enums.StatusEntrega;
import br.com.ms.logistica.domain.enums.StatusEntregador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Objects;

@Controller
public class CancelaEntregaController {

    private final BuscaEntrega buscaEntrega;
    private final AtualizaEntrega atualizaEntrega;
    private final CancelaPedido cancelaPedido;
    private final AtualizaEntregador atualizaEntregador;

    @Autowired
    public CancelaEntregaController(BuscaEntrega buscaEntrega,
                                    AtualizaEntrega atualizaEntrega,
                                    CancelaPedido cancelaPedido,
                                    AtualizaEntregador atualizaEntregador) {
        this.buscaEntrega = buscaEntrega;
        this.atualizaEntrega = atualizaEntrega;
        this.cancelaPedido = cancelaPedido;
        this.atualizaEntregador = atualizaEntregador;
    }

    public void run(Long id) {
        EntregaEntity entregaEntity = buscaEntrega.run(id);
        if (entregaEntity.getStatus().equals(StatusEntrega.CANCELADA))
            throw new EntregaException("entrega já está cancelada");
        if (entregaEntity.getStatus().equals(StatusEntrega.CONCLUIDA))
            throw new EntregaException("entrega já está concluída");
        if (entregaEntity.getStatus().equals(StatusEntrega.EM_TRANSITO))
            throw new EntregaException("entrega em trânsito não pode ser cancelada");
        entregaEntity.setStatus(StatusEntrega.CANCELADA);
        atualizaEntrega.run(id, entregaEntity);
        cancelaPedido.run(entregaEntity.getPedidoId());
        if (Objects.nonNull(entregaEntity.getEntregador())) {
            EntregadorEntity entregadorEntity = entregaEntity.getEntregador();
            entregadorEntity.setStatus(StatusEntregador.DISPONIVEL);
            atualizaEntregador.run(entregadorEntity.getId(), entregadorEntity);
        }
    }

}
