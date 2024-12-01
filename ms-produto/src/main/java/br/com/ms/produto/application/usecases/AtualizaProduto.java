package br.com.ms.produto.application.usecases;

import br.com.ms.produto.application.gateways.ProdutoGateway;
import br.com.ms.produto.domain.entities.ProdutoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AtualizaProduto {

    private final ProdutoGateway produtoGateway;

    @Autowired
    public AtualizaProduto(ProdutoGateway produtoGateway) {
        this.produtoGateway = produtoGateway;
    }

    public ProdutoEntity run(Long id, ProdutoEntity entity) {
        return produtoGateway.atualizar(id, entity);
    }

}
