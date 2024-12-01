package br.com.ms.logistica.application.usecases;

import br.com.ms.logistica.application.gateways.EntregaGateway;
import br.com.ms.logistica.domain.entities.EntregaEntity;
import br.com.ms.logistica.domain.enums.StatusEntrega;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuscaEntrega {

    private final EntregaGateway entregaGateway;

    @Autowired
    public BuscaEntrega(EntregaGateway entregaGateway) {
        this.entregaGateway = entregaGateway;
    }

    public EntregaEntity run(Long id) {
        return entregaGateway.buscarPorId(id);
    }

}
