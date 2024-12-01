package br.com.ms.pedido.application.usecases;

import br.com.ms.pedido.application.dto.PedidoDTO;
import br.com.ms.pedido.application.exceptions.PedidoException;
import br.com.ms.pedido.domain.entities.PedidoEntity;
import br.com.ms.pedido.domain.enums.StatusPedido;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ValidaPedido {

    public PedidoEntity run(PedidoDTO pedidoDTO) {
        Map<Long, Integer> produtos = new HashMap<>();
        pedidoDTO.getProdutos().stream().filter(p -> p.getId() > 0 && p.getQuantidade() > 0).forEach(produto -> {
            if (produtos.containsKey(produto.getId()))
                produtos.replace(produto.getId(), produto.getQuantidade() + produtos.get(produto.getId()));
            if (!produtos.containsKey(produto.getId()))
                produtos.put(produto.getId(), produto.getQuantidade());
        });

        PedidoEntity pedidoEntity;
        try {
            pedidoEntity = new PedidoEntity(pedidoDTO.getClienteId(), StatusPedido.get(pedidoDTO.getStatus()), produtos);
        } catch (IllegalArgumentException exception) {
            throw new PedidoException("erro ao validar pedido: " + exception.getMessage());
        }
        return pedidoEntity;
    }

}
