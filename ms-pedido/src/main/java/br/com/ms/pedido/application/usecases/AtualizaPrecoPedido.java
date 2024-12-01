package br.com.ms.pedido.application.usecases;

import org.springframework.stereotype.Service;

@Service
public class AtualizaPrecoPedido {

    public Double run(Integer quantidade, Double preco) {
        return quantidade * preco;
    }

}
