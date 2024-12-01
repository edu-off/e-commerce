package br.com.ms.pedido.application.gateways;

import br.com.ms.pedido.application.dto.EntregaDTO;

public interface EntregaGateway {

    EntregaDTO registraEntrega(EntregaDTO entregaDTO);

}
