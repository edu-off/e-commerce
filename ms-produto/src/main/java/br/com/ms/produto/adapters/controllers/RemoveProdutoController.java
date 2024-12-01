package br.com.ms.produto.adapters.controllers;

import br.com.ms.produto.application.usecases.RemoveProduto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class RemoveProdutoController {

    private final RemoveProduto removeProduto;

    @Autowired
    public RemoveProdutoController(RemoveProduto removeProduto) {
        this.removeProduto = removeProduto;
    }

    public void run(Long id) {
        removeProduto.run(id);
    }

}
