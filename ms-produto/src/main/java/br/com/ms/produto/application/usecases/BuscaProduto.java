package br.com.ms.produto.application.usecases;

import br.com.ms.produto.application.gateways.ProdutoGateway;
import br.com.ms.produto.domain.entities.ProdutoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class BuscaProduto {

    private final ProdutoGateway produtoGateway;

    @Autowired
    public BuscaProduto(ProdutoGateway produtoGateway) {
        this.produtoGateway = produtoGateway;
    }

    public ProdutoEntity porId(Long id) {
        ProdutoEntity entity = produtoGateway.buscarPorId(id);
        if (Objects.isNull(entity))
            throw new NoSuchElementException("produto não encontrado");
        return entity;
    }

    public Page<ProdutoEntity> porNome(String nome, Pageable pageable) {
        return produtoGateway.buscarPorNome(nome, pageable);
    }

    public Page<ProdutoEntity> porDescricao(String descricao, Pageable pageable) {
        return produtoGateway.buscarPorDescricao(descricao, pageable);
    }

    public Page<ProdutoEntity> porCategoria(String categoria, Pageable pageable) {
        return produtoGateway.buscarPorCategoria(categoria, pageable);
    }

}
