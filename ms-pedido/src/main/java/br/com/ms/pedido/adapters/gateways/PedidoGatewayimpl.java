package br.com.ms.pedido.adapters.gateways;

import br.com.ms.pedido.application.gateways.PedidoGateway;
import br.com.ms.pedido.domain.entities.PedidoEntity;
import br.com.ms.pedido.infrastructure.models.PedidoModel;
import br.com.ms.pedido.infrastructure.repositories.PedidoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PedidoGatewayimpl implements PedidoGateway {

    private final PedidoRepository pedidoRepository;
    private final ModelMapper mapper;

    @Autowired
    public PedidoGatewayimpl(PedidoRepository pedidoRepository, ModelMapper mapper) {
        this.pedidoRepository = pedidoRepository;
        this.mapper = mapper;
    }

    @Override
    public PedidoEntity salvar(PedidoEntity pedidoEntity) {
        PedidoModel pedidoModel = mapper.map(pedidoEntity, PedidoModel.class);
        pedidoModel.setId(null);
        pedidoModel.setProdutos(transformMapToList(pedidoEntity.getProdutos()));
        PedidoModel pedidoModelCreated = pedidoRepository.save(pedidoModel);
        PedidoEntity pedidoEntityCreated = mapper.map(pedidoModelCreated, PedidoEntity.class);
        pedidoEntityCreated.setProdutos(pedidoEntity.getProdutos());
        return pedidoEntityCreated;

    }

    @Override
    public PedidoEntity atualizar(Long id, PedidoEntity pedidoEntity) {
        Optional<PedidoModel> optional = pedidoRepository.findById(id);
        if (optional.isEmpty())
            throw new NoSuchElementException("pedido não encontrado");
        PedidoModel pedidoModel = optional.get();
        pedidoModel.setStatus(pedidoEntity.getStatus());
        pedidoModel.setPreco(pedidoEntity.getPreco());
        pedidoModel.setProdutos(transformMapToList(pedidoEntity.getProdutos()));
        pedidoModel.setDataAbertura(pedidoEntity.getDataAbertura());
        pedidoModel.setDataConfirmacao(pedidoEntity.getDataConfirmacao());
        pedidoModel.setDataCancelamento(pedidoEntity.getDataCancelamento());
        pedidoModel.setDataConclusao(pedidoEntity.getDataConclusao());
        PedidoModel pedidoModelUpdated = pedidoRepository.save(pedidoModel);
        PedidoEntity pedidoEntityUpdated = mapper.map(pedidoModelUpdated, PedidoEntity.class);
        pedidoEntityUpdated.setProdutos(pedidoEntity.getProdutos());
        return pedidoEntityUpdated;
    }

    @Override
    public PedidoEntity buscarPorId(Long id) {
        Optional<PedidoModel> optional = pedidoRepository.findById(id);
        if (optional.isEmpty())
            throw new NoSuchElementException("pedido não encontrado");
        PedidoModel pedidoModel = optional.get();
        PedidoEntity pedidoEntity = mapper.map(pedidoModel, PedidoEntity.class);
        if (Objects.nonNull(pedidoModel.getProdutos()))
            pedidoEntity.setProdutos(transformListToMap(pedidoModel.getProdutos()));
        return pedidoEntity;
    }

    private static List<Map<String, Object>> transformMapToList(Map<Long, Integer> map) {
        List<Map<String, Object>> produtos = new ArrayList<>();
        map.forEach((id, quantidade) -> produtos.add(Map.of("id", id, "quantidade", quantidade)));
        return produtos;
    }

    private static Map<Long, Integer> transformListToMap(List<Map<String, Object>> list) {
        Map<Long, Integer> produtos = new HashMap<>();
        list.forEach(produto -> produtos.put((Long) produto.get("id"), (Integer) produto.get("quantidade")));
        return produtos;
    }

}
