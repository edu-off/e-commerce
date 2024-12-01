package br.com.ms.produto.adapters.controllers;

import br.com.ms.produto.adapters.presenters.ProdutoPresenter;
import br.com.ms.produto.application.dto.ProdutoDTO;
import br.com.ms.produto.application.usecases.AtualizaProduto;
import br.com.ms.produto.application.usecases.BuscaProduto;
import br.com.ms.produto.application.usecases.ValidaProduto;
import br.com.ms.produto.domain.entities.ProdutoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class AtualizaProdutoController {

    private final BuscaProduto buscaProduto;
    private final ValidaProduto validaProduto;
    private final AtualizaProduto atualizaProduto;
    private final ProdutoPresenter presenter;

    @Autowired
    public AtualizaProdutoController(BuscaProduto buscaProduto,
                                     ValidaProduto validaProduto,
                                     AtualizaProduto atualizaProduto,
                                     ProdutoPresenter presenter) {
        this.buscaProduto = buscaProduto;
        this.validaProduto = validaProduto;
        this.atualizaProduto = atualizaProduto;
        this.presenter = presenter;
    }

    public ProdutoDTO run(Long id, ProdutoDTO dto) {
        ProdutoEntity entityRetrieved = buscaProduto.porId(id);
        ProdutoEntity entityValidated = validaProduto.run(dto);
        entityValidated.setId(id);
        entityValidated.setQuantidade(entityRetrieved.getQuantidade());
        ProdutoEntity entityUpdated = atualizaProduto.run(id, entityValidated);
        return presenter.transform(entityUpdated);
    }

}
