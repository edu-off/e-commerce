package br.com.ms.produto.adapters.controllers;

import br.com.ms.produto.adapters.presenters.ProdutoPresenter;
import br.com.ms.produto.application.dto.ProdutoDTO;
import br.com.ms.produto.application.usecases.BuscaProduto;
import br.com.ms.produto.domain.entities.ProdutoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class BuscaProdutoController {

    private final BuscaProduto buscaProduto;
    private final ProdutoPresenter presenter;

    @Autowired
    public BuscaProdutoController(BuscaProduto buscaProduto, ProdutoPresenter presenter) {
        this.buscaProduto = buscaProduto;
        this.presenter = presenter;
    }

    public ProdutoDTO porId(Long id) {
        ProdutoEntity entity = buscaProduto.porId(id);
        return presenter.transform(entity);
    }

    public Page<ProdutoDTO> porNome(String nome, Pageable pageable) {
        Page<ProdutoEntity> entities = buscaProduto.porNome(nome, pageable);
        return presenter.transform(entities, pageable);
    }

    public Page<ProdutoDTO> porDescricao(String descricao, Pageable pageable) {
        Page<ProdutoEntity> entities = buscaProduto.porDescricao(descricao, pageable);
        return presenter.transform(entities, pageable);
    }

    public Page<ProdutoDTO> porCategoria(String categoria, Pageable pageable) {
        Page<ProdutoEntity> entities = buscaProduto.porCategoria(categoria, pageable);
        return presenter.transform(entities, pageable);
    }

}
