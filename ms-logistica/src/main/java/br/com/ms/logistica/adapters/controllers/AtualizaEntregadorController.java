package br.com.ms.logistica.adapters.controllers;

import br.com.ms.logistica.adapters.presenters.EntregadorPresenter;
import br.com.ms.logistica.application.dto.EntregadorDTO;
import br.com.ms.logistica.application.usecases.AtualizaEntregador;
import br.com.ms.logistica.application.usecases.ValidaEntregador;
import br.com.ms.logistica.domain.entities.EntregadorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class AtualizaEntregadorController {

    private final ValidaEntregador validaEntregador;
    private final AtualizaEntregador atualizaEntregador;
    private final EntregadorPresenter presenter;

    @Autowired
    public AtualizaEntregadorController(ValidaEntregador validaEntregador, AtualizaEntregador atualizaEntregador, EntregadorPresenter presenter) {
        this.validaEntregador = validaEntregador;
        this.atualizaEntregador = atualizaEntregador;
        this.presenter = presenter;
    }

    public EntregadorDTO run(Long id, EntregadorDTO dto) {
        EntregadorEntity entity = validaEntregador.run(dto);
        return presenter.transform(atualizaEntregador.run(id, entity));
    }

}
