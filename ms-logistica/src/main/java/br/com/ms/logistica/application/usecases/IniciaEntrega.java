package br.com.ms.logistica.application.usecases;

import br.com.ms.logistica.application.gateways.EntregaGateway;
import br.com.ms.logistica.application.gateways.EntregadorGateway;
import br.com.ms.logistica.domain.entities.EntregaEntity;
import br.com.ms.logistica.domain.entities.EntregadorEntity;
import br.com.ms.logistica.domain.enums.StatusEntrega;
import br.com.ms.logistica.domain.enums.StatusEntregador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IniciaEntrega {

    private final EntregaGateway entregaGateway;
    private final EntregadorGateway entregadorGateway;

    @Autowired
    public IniciaEntrega(EntregaGateway entregaGateway, EntregadorGateway entregadorGateway) {
        this.entregaGateway = entregaGateway;
        this.entregadorGateway = entregadorGateway;
    }

    public void run(EntregaEntity entregaEntity, EntregadorEntity entregadorEntity) {
        entregaEntity.setStatus(StatusEntrega.EM_TRANSITO);
        entregaEntity.setEntregador(entregadorEntity);
        entregaGateway.atualizar(entregaEntity.getId(), entregaEntity);
        entregadorEntity.setStatus(StatusEntregador.EM_TRANSITO);
        entregadorGateway.atualizar(entregadorEntity.getId(), entregadorEntity);
    }

}
