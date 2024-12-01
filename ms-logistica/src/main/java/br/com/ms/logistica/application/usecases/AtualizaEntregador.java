package br.com.ms.logistica.application.usecases;

import br.com.ms.logistica.application.gateways.EntregadorGateway;
import br.com.ms.logistica.domain.entities.EntregadorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AtualizaEntregador {

    private final EntregadorGateway entregadorGateway;

    @Autowired
    public AtualizaEntregador(EntregadorGateway entregadorGateway) {
        this.entregadorGateway = entregadorGateway;
    }

    public EntregadorEntity run(Long id, EntregadorEntity entregadorEntity) {
        return entregadorGateway.atualizar(id, entregadorEntity);
    }

}
