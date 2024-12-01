package br.com.ms.logistica.application.usecases;

import br.com.ms.logistica.application.gateways.EntregaGateway;
import br.com.ms.logistica.domain.entities.EntregaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalvaEntrega {

    private final EntregaGateway entregaGateway;

    @Autowired
    public SalvaEntrega(EntregaGateway entregaGateway) {
        this.entregaGateway = entregaGateway;
    }

    public EntregaEntity run(EntregaEntity entregaEntity) {
        return entregaGateway.salvar(entregaEntity);
    }

}
