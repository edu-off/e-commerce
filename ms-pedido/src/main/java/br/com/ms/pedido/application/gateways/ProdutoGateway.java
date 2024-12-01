package br.com.ms.pedido.application.gateways;

import br.com.ms.pedido.application.dto.ProdutoDTO;

public interface ProdutoGateway {

    ProdutoDTO buscaProduto(Long id);
    void incrementaEstoque(Long id, Integer quantidade);
    void decrementaEstoque(Long id, Integer quantidade);

}
