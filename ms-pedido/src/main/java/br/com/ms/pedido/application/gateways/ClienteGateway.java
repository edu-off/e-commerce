package br.com.ms.pedido.application.gateways;

import br.com.ms.pedido.application.dto.ClienteDTO;

public interface ClienteGateway {

    ClienteDTO buscaPorId(Long id);

}
