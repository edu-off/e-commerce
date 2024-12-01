package br.com.ms.produto.adapters.controllers;

import br.com.ms.produto.adapters.presenters.ProdutoPresenter;
import br.com.ms.produto.application.dto.ProdutoDTO;
import br.com.ms.produto.application.usecases.SalvaProduto;
import br.com.ms.produto.application.usecases.ValidaProduto;
import br.com.ms.produto.domain.entities.ProdutoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class RegistraProdutoController {

    private final ValidaProduto validaProduto;
    private final SalvaProduto salvaProduto;
    private final ProdutoPresenter presenter;

    @Autowired
    public RegistraProdutoController(ValidaProduto validaProduto,
                                     SalvaProduto salvaProduto,
                                     ProdutoPresenter presenter) {
        this.validaProduto = validaProduto;
        this.salvaProduto = salvaProduto;
        this.presenter = presenter;
    }

    public ProdutoDTO run(ProdutoDTO dto) {
        ProdutoEntity entity = validaProduto.run(dto);
        ProdutoEntity entityCreated = salvaProduto.run(entity);
        return presenter.transform(entityCreated);
    }

}
