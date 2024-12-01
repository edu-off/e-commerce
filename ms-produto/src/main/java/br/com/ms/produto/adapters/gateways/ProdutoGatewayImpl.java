package br.com.ms.produto.adapters.gateways;

import br.com.ms.produto.application.gateways.ProdutoGateway;
import br.com.ms.produto.domain.entities.ProdutoEntity;
import br.com.ms.produto.infrastructure.models.ProdutoModel;
import br.com.ms.produto.infrastructure.repositories.ProdutoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ProdutoGatewayImpl implements ProdutoGateway {

    private final ProdutoRepository produtoRepository;
    private final ModelMapper mapper;

    @Autowired
    public ProdutoGatewayImpl(ProdutoRepository produtoRepository, ModelMapper mapper) {
        this.produtoRepository = produtoRepository;
        this.mapper = mapper;
    }

    @Override
    public ProdutoEntity salvar(ProdutoEntity entity) {
        ProdutoModel model = mapper.map(entity, ProdutoModel.class);
        ProdutoModel modelSaved = produtoRepository.save(model);
        return mapper.map(modelSaved, ProdutoEntity.class);
    }

    @Override
    public ProdutoEntity atualizar(Long id, ProdutoEntity entity) {
        Optional<ProdutoModel> optional = produtoRepository.findById(id);
        if (optional.isEmpty())
            throw new NoSuchElementException("produto não encontrado");

        ProdutoModel produtoModel = optional.get();
        produtoModel.setNome(entity.getNome());
        produtoModel.setDescricao(entity.getDescricao());
        produtoModel.setPreco(entity.getPreco());
        produtoModel.setQuantidade(entity.getQuantidade());
        produtoModel.setCategoria(entity.getCategoria());

        ProdutoModel modelUpdated = produtoRepository.save(produtoModel);
        return mapper.map(modelUpdated, ProdutoEntity.class);
    }

    @Override
    public void remover(Long id) {
        if (produtoRepository.findById(id).isEmpty())
            throw new NoSuchElementException("produto não encontrado");

        produtoRepository.deleteById(id);
    }

    @Override
    public ProdutoEntity buscarPorId(Long id) {
        Optional<ProdutoModel> optional = produtoRepository.findById(id);
        ProdutoEntity produtoEntity = optional.map(model -> mapper.map(model, ProdutoEntity.class)).orElse(null);
        if (Objects.isNull(produtoEntity))
            return null;
        return produtoEntity;
    }

    @Override
    public Page<ProdutoEntity> buscarPorNome(String nome, Pageable pageable) {
        Page<ProdutoModel> models = produtoRepository.findByNomeContainingIgnoreCase(nome, pageable);
        return transformPageModelToPageDomain(models, pageable);
    }

    @Override
    public Page<ProdutoEntity> buscarPorDescricao(String descricao, Pageable pageable) {
        Page<ProdutoModel> models = produtoRepository.findByDescricaoContainingIgnoreCase(descricao, pageable);
        return transformPageModelToPageDomain(models, pageable);
    }

    @Override
    public Page<ProdutoEntity> buscarPorCategoria(String categoria, Pageable pageable) {
        Page<ProdutoModel> models = produtoRepository.findByCategoriaContainingIgnoreCase(categoria, pageable);
        return transformPageModelToPageDomain(models, pageable);
    }

    private Page<ProdutoEntity> transformPageModelToPageDomain(Page<ProdutoModel> queryResults, Pageable pageable) {
        List<ProdutoModel> models = queryResults.getContent();
        List<ProdutoEntity> entities = new ArrayList<>();
        models.forEach(model -> entities.add(mapper.map(model, ProdutoEntity.class)));
        return PageableExecutionUtils.getPage(entities, pageable, entities::size);
    }

}
