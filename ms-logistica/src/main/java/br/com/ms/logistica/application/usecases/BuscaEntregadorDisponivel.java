package br.com.ms.logistica.application.usecases;

import br.com.ms.logistica.application.gateways.EntregadorGateway;
import br.com.ms.logistica.domain.entities.EntregadorEntity;
import br.com.ms.logistica.domain.enums.StatusEntregador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuscaEntregadorDisponivel {

    private final EntregadorGateway entregadorGateway;

    @Autowired
    public BuscaEntregadorDisponivel(EntregadorGateway entregadorGateway) {
        this.entregadorGateway = entregadorGateway;
    }

    public EntregadorEntity run() {
        List<EntregadorEntity> entregadores = entregadorGateway.buscarPorStatus(StatusEntregador.DISPONIVEL);
        return entregadores.stream().findFirst().orElse(null);
    }

}
