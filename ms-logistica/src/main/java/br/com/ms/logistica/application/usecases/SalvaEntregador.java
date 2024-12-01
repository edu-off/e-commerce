package br.com.ms.logistica.application.usecases;

import br.com.ms.logistica.application.gateways.EntregadorGateway;
import br.com.ms.logistica.domain.entities.EntregadorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalvaEntregador {

    private final EntregadorGateway entregadorGateway;

    @Autowired
    public SalvaEntregador(EntregadorGateway entregadorGateway) {
        this.entregadorGateway = entregadorGateway;
    }

    public EntregadorEntity run(EntregadorEntity entregadorEntity) {
        return entregadorGateway.salvar(entregadorEntity);
    }

}
