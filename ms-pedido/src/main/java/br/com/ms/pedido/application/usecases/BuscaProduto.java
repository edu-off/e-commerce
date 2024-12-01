package br.com.ms.pedido.application.usecases;

import br.com.ms.pedido.application.dto.ProdutoDTO;
import br.com.ms.pedido.application.gateways.ProdutoGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuscaProduto {

    private final ProdutoGateway produtoGateway;

    @Autowired
    public BuscaProduto(ProdutoGateway produtoGateway) {
        this.produtoGateway = produtoGateway;
    }

    public ProdutoDTO run(Long id) {
        return produtoGateway.buscaProduto(id);
    }

}
