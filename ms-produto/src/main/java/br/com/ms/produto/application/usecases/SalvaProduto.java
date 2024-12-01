package br.com.ms.produto.application.usecases;

import br.com.ms.produto.application.gateways.ProdutoGateway;
import br.com.ms.produto.domain.entities.ProdutoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalvaProduto {

    private final ProdutoGateway produtoGateway;

    @Autowired
    public SalvaProduto(ProdutoGateway produtoGateway) {
        this.produtoGateway = produtoGateway;
    }

    public ProdutoEntity run(ProdutoEntity entity) {
        return produtoGateway.salvar(entity);
    }

}
