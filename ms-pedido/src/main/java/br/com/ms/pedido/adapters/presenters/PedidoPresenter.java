package br.com.ms.pedido.adapters.presenters;

import br.com.ms.pedido.application.dto.PedidoDTO;
import br.com.ms.pedido.application.dto.ProdutoDTO;
import br.com.ms.pedido.domain.entities.PedidoEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class PedidoPresenter {

    private final ModelMapper mapper;

    @Autowired
    public PedidoPresenter(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public PedidoDTO transform(PedidoEntity pedidoEntity) {
        PedidoDTO pedidoDTO = mapper.map(pedidoEntity, PedidoDTO.class);
        if (Objects.nonNull(pedidoEntity.getProdutos())) {
            List<ProdutoDTO> produtos = new ArrayList<>();
            pedidoEntity.getProdutos().forEach((id, quantidade) -> {
                produtos.add(new ProdutoDTO(id, quantidade, null));
            });
            pedidoDTO.setProdutos(produtos);
        }
        return pedidoDTO;
    }

}
