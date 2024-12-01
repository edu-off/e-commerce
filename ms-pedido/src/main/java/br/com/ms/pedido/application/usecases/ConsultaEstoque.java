package br.com.ms.pedido.application.usecases;

import br.com.ms.pedido.application.exceptions.PedidoException;
import org.springframework.stereotype.Service;

@Service
public class ConsultaEstoque {

    public void run(Integer quantidadeAtual, Integer quantidadePedido) {
        if (quantidadePedido > quantidadeAtual)
            throw new PedidoException("erro ao confirmar pedido, um dos produtos não possui quantidade requerida em estoque");
    }

}
