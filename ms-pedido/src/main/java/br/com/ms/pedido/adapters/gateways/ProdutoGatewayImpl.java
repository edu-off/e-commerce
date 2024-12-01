package br.com.ms.pedido.adapters.gateways;

import br.com.ms.pedido.adapters.clients.ProdutoFeignClient;
import br.com.ms.pedido.application.dto.ProdutoDTO;
import br.com.ms.pedido.application.gateways.ProdutoGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProdutoGatewayImpl implements ProdutoGateway {

    private final ProdutoFeignClient client;

    @Autowired
    public ProdutoGatewayImpl(ProdutoFeignClient client) {
        this.client = client;
    }

    @Override
    public ProdutoDTO buscaProduto(Long id) {
        return client.buscaProduto(id);
    }

    @Override
    public void incrementaEstoque(Long id, Integer quantidade) {
        client.incrementaEstoque(id, quantidade);
    }

    @Override
    public void decrementaEstoque(Long id, Integer quantidade) {
        client.decrementaEstoque(id, quantidade);
    }

}
