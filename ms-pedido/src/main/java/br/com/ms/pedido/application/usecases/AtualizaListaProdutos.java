package br.com.ms.pedido.application.usecases;

import br.com.ms.pedido.application.dto.ProdutoDTO;
import br.com.ms.pedido.domain.entities.PedidoEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AtualizaListaProdutos {

    public PedidoEntity run(PedidoEntity pedidoEntity, List<ProdutoDTO> listaProdutos) {
        Map<Long, Integer> produtos = new HashMap<>(pedidoEntity.getProdutos());
        listaProdutos.forEach(produto -> {
            if (produtos.containsKey(produto.getId()))
                produtos.replace(produto.getId(), produto.getQuantidade());
            if (!produtos.containsKey(produto.getId()))
                produtos.put(produto.getId(), produto.getQuantidade());
        });
        pedidoEntity.setProdutos(produtos);
        return pedidoEntity;
    }

}
