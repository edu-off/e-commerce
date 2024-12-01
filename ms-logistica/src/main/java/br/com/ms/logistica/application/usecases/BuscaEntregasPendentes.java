package br.com.ms.logistica.application.usecases;

import br.com.ms.logistica.application.gateways.EntregaGateway;
import br.com.ms.logistica.domain.entities.EntregaEntity;
import br.com.ms.logistica.domain.enums.StatusEntrega;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuscaEntregasPendentes {

    private final EntregaGateway entregaGateway;

    @Autowired
    public BuscaEntregasPendentes(EntregaGateway entregaGateway) {
        this.entregaGateway = entregaGateway;
    }

    public List<EntregaEntity> run() {
        return entregaGateway.buscarPorStatus(StatusEntrega.PENDENTE);
    }

}
