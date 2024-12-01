package br.com.ms.logistica.adapters.controllers;

import br.com.ms.logistica.application.usecases.BuscaEntregadorDisponivel;
import br.com.ms.logistica.application.usecases.BuscaEntregasPendentes;
import br.com.ms.logistica.application.usecases.IniciaEntrega;
import br.com.ms.logistica.application.usecases.RecuperaDadosCliente;
import br.com.ms.logistica.domain.entities.EntregaEntity;
import br.com.ms.logistica.domain.entities.EntregadorEntity;
import br.com.ms.logistica.domain.enums.StatusEntrega;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Objects;

@Controller
public class IniciaEntregaController {

    private final BuscaEntregasPendentes buscaEntregasPendentes;
    private final BuscaEntregadorDisponivel buscaEntregadorDisponivel;
    private final RecuperaDadosCliente recuperaDadosCliente;
    private final IniciaEntrega iniciaEntrega;

    @Autowired
    public IniciaEntregaController(BuscaEntregasPendentes buscaEntregasPendentes,
                                   BuscaEntregadorDisponivel buscaEntregadorDisponivel,
                                   RecuperaDadosCliente recuperaDadosCliente,
                                   IniciaEntrega iniciaEntrega) {
        this.buscaEntregasPendentes = buscaEntregasPendentes;
        this.buscaEntregadorDisponivel = buscaEntregadorDisponivel;
        this.recuperaDadosCliente = recuperaDadosCliente;
        this.iniciaEntrega = iniciaEntrega;
    }

    @Scheduled(cron = "0 0/15 * * * *", zone = "America/Sao_Paulo")
    public void run() {
        List<EntregaEntity> entregas = buscaEntregasPendentes.run();
        entregas.forEach(entrega -> {
            EntregadorEntity entregador = buscaEntregadorDisponivel.run();
            if (Objects.nonNull(entregador) && entrega.getStatus().equals(StatusEntrega.PENDENTE))
                iniciaEntrega.run(recuperaDadosCliente.porId(entrega), entregador);
        });
    }

}
