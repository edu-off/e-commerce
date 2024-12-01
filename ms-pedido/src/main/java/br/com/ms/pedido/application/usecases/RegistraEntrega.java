package br.com.ms.pedido.application.usecases;

import br.com.ms.pedido.application.dto.EntregaDTO;
import br.com.ms.pedido.application.gateways.EntregaGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistraEntrega {

    private final EntregaGateway entregaGateway;

    @Autowired
    public RegistraEntrega(EntregaGateway entregaGateway) {
        this.entregaGateway = entregaGateway;
    }

    public EntregaDTO run(Long clienteId, Long pedidoId) {
        EntregaDTO entregaDTO = new EntregaDTO(null, "PENDENTE", clienteId, pedidoId);
        return entregaGateway.registraEntrega(entregaDTO);
    }

}
