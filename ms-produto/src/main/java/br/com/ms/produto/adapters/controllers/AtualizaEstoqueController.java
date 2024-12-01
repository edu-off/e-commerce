package br.com.ms.produto.adapters.controllers;

import br.com.ms.produto.adapters.presenters.ProdutoPresenter;
import br.com.ms.produto.application.dto.ProdutoDTO;
import br.com.ms.produto.application.usecases.AtualizaEstoque;
import br.com.ms.produto.application.usecases.AtualizaProduto;
import br.com.ms.produto.application.usecases.BuscaProduto;
import br.com.ms.produto.domain.entities.ProdutoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class AtualizaEstoqueController {

    private final BuscaProduto buscaProduto;
    private final AtualizaEstoque atualizaEstoque;
    private final AtualizaProduto atualizaProduto;
    private final ProdutoPresenter presenter;

    @Autowired
    public AtualizaEstoqueController(BuscaProduto buscaProduto,
                                     AtualizaEstoque atualizaEstoque,
                                     AtualizaProduto atualizaProduto,
                                     ProdutoPresenter presenter) {
        this.buscaProduto = buscaProduto;
        this.atualizaEstoque = atualizaEstoque;
        this.atualizaProduto = atualizaProduto;
        this.presenter = presenter;
    }

    public ProdutoDTO incrementa(Long id, Integer quantidade) {
        ProdutoEntity entity = buscaProduto.porId(id);
        ProdutoEntity entityUpdated = atualizaEstoque.incrementa(entity, quantidade);
        return presenter.transform(atualizaProduto.run(id, entityUpdated));
    }

    public ProdutoDTO decrementa(Long id, Integer quantidade) {
        ProdutoEntity entity = buscaProduto.porId(id);
        ProdutoEntity entityUpdated = atualizaEstoque.decrementa(entity, quantidade);
        return presenter.transform(atualizaProduto.run(id, entityUpdated));
    }

}
