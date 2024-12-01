package br.com.ms.pedido.adapters.controllers;

import br.com.ms.pedido.adapters.presenters.PedidoPresenter;
import br.com.ms.pedido.application.dto.PedidoDTO;
import br.com.ms.pedido.application.usecases.BuscaPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BuscaPedidoController {

    private final BuscaPedido buscaPedido;
    private final PedidoPresenter presenter;

    @Autowired
    public BuscaPedidoController(BuscaPedido buscaPedido, PedidoPresenter presenter) {
        this.buscaPedido = buscaPedido;
        this.presenter = presenter;
    }

    public PedidoDTO run(Long id) {
        return presenter.transform(buscaPedido.run(id));
    }

}
