package br.com.ms.produto.application.usecases;

import br.com.ms.produto.application.gateways.ProdutoGateway;
import br.com.ms.produto.domain.entities.ProdutoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RemoveProduto {

    private final ProdutoGateway produtoGateway;

    @Autowired
    public RemoveProduto(ProdutoGateway produtoGateway) {
        this.produtoGateway = produtoGateway;
    }

    public void run(Long id) {
        produtoGateway.remover(id);
    }

}
