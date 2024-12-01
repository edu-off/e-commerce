package br.com.ms.produto.application.usecases;

import br.com.ms.produto.domain.entities.ProdutoEntity;
import org.springframework.stereotype.Service;

@Service
public class AtualizaEstoque {

    public ProdutoEntity incrementa(ProdutoEntity entity, Integer quantidade) {
        entity.adicionaQuantidade(quantidade);
        return entity;
    }

    public ProdutoEntity decrementa(ProdutoEntity entity, Integer quantidade) {
        entity.subtraiQuantidade(quantidade);
        return entity;
    }

}
