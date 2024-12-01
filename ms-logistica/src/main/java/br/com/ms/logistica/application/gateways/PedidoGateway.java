package br.com.ms.logistica.application.gateways;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

@Component
public interface PedidoGateway {

    void concluiPedido(@PathVariable Long id);
    void cancelaPedido(@PathVariable Long id);

}
