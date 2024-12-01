package br.com.ms.logistica.adapters.controllers;

import br.com.ms.logistica.adapters.presenters.EntregaPresenter;
import br.com.ms.logistica.application.dto.EntregaDTO;
import br.com.ms.logistica.application.usecases.BuscaCliente;
import br.com.ms.logistica.application.usecases.BuscaPedido;
import br.com.ms.logistica.application.usecases.SalvaEntrega;
import br.com.ms.logistica.application.usecases.ValidaEntrega;
import br.com.ms.logistica.domain.entities.EntregaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class RegistraEntregaController {

    private final ValidaEntrega validaEntrega;
    private final BuscaCliente buscaCliente;
    private final BuscaPedido buscaPedido;
    private final SalvaEntrega salvaEntrega;
    private final EntregaPresenter presenter;

    @Autowired
    public RegistraEntregaController(ValidaEntrega validaEntrega,
                                     BuscaCliente buscaCliente,
                                     BuscaPedido buscaPedido,
                                     SalvaEntrega salvaEntrega,
                                     EntregaPresenter presenter) {
        this.validaEntrega = validaEntrega;
        this.buscaCliente = buscaCliente;
        this.buscaPedido = buscaPedido;
        this.salvaEntrega = salvaEntrega;
        this.presenter = presenter;
    }

    public EntregaDTO run(EntregaDTO dto) {
        EntregaEntity entity = validaEntrega.run(dto);
        buscaCliente.run(dto.getClienteId());
        buscaPedido.run(dto.getPedidoId());
        return presenter.transform(salvaEntrega.run(entity));
    }

}
