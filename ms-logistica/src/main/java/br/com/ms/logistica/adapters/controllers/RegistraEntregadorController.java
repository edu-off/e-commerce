package br.com.ms.logistica.adapters.controllers;

import br.com.ms.logistica.adapters.presenters.EntregadorPresenter;
import br.com.ms.logistica.application.dto.EntregadorDTO;
import br.com.ms.logistica.application.usecases.SalvaEntregador;
import br.com.ms.logistica.application.usecases.ValidaEntregador;
import br.com.ms.logistica.domain.entities.EntregadorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class RegistraEntregadorController {

    private final ValidaEntregador validaEntregador;
    private final SalvaEntregador salvaEntregador;
    private final EntregadorPresenter presenter;

    @Autowired
    public RegistraEntregadorController(ValidaEntregador validaEntregador, SalvaEntregador salvaEntregador,EntregadorPresenter presenter) {
        this.validaEntregador = validaEntregador;
        this.salvaEntregador = salvaEntregador;
        this.presenter = presenter;
    }

    public EntregadorDTO run(EntregadorDTO dto) {
        EntregadorEntity entity = validaEntregador.run(dto);
        return presenter.transform(salvaEntregador.run(entity));
    }

}
