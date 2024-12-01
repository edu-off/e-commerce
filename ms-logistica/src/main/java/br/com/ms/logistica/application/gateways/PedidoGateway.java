package br.com.ms.logistica.application.gateways;

import br.com.ms.logistica.application.dto.PedidoDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

@Component
public interface PedidoGateway {

    PedidoDTO buscaPorId(@PathVariable Long id);
    void concluiPedido(@PathVariable Long id);
    void cancelaPedido(@PathVariable Long id);

}
