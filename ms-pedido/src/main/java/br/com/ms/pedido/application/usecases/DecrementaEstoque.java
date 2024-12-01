package br.com.ms.pedido.application.usecases;

import br.com.ms.pedido.application.gateways.ProdutoGateway;
import br.com.ms.pedido.domain.entities.PedidoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DecrementaEstoque {

    private final ProdutoGateway produtoGateway;

    @Autowired
    public DecrementaEstoque(ProdutoGateway produtoGateway) {
        this.produtoGateway = produtoGateway;
    }

    public void run(PedidoEntity pedidoEntity) {
        pedidoEntity.getProdutos().forEach(produtoGateway::decrementaEstoque);
    }

}
