package br.com.ms.pedido.application.gateways;

import br.com.ms.pedido.domain.entities.PedidoEntity;

public interface PedidoGateway {

    PedidoEntity salvar(PedidoEntity pedidoEntity);
    PedidoEntity atualizar(Long id, PedidoEntity pedidoEntity);
    PedidoEntity buscarPorId(Long id);

}
