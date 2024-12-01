package br.com.ms.logistica.adapters.controllers;

import br.com.ms.logistica.adapters.presenters.EntregaPresenter;
import br.com.ms.logistica.application.dto.EntregaDTO;
import br.com.ms.logistica.application.usecases.SalvaEntrega;
import br.com.ms.logistica.application.usecases.ValidaEntrega;
import br.com.ms.logistica.domain.entities.EntregaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class RegistraEntregaController {

    private final ValidaEntrega validaEntrega;
    private final SalvaEntrega salvaEntrega;
    private final EntregaPresenter presenter;

    @Autowired
    public RegistraEntregaController(ValidaEntrega validaEntrega, SalvaEntrega salvaEntrega, EntregaPresenter presenter) {
        this.validaEntrega = validaEntrega;
        this.salvaEntrega = salvaEntrega;
        this.presenter = presenter;
    }

    public EntregaDTO run(EntregaDTO dto) {
        EntregaEntity entity = validaEntrega.run(dto);
        return presenter.transform(salvaEntrega.run(entity));
    }

}
