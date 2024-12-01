package br.com.ms.produto.application.gateways;

import br.com.ms.produto.domain.entities.ProdutoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProdutoGateway {

    ProdutoEntity salvar(ProdutoEntity entity);
    ProdutoEntity atualizar(Long id, ProdutoEntity entity);
    void remover(Long id);
    ProdutoEntity buscarPorId(Long id);
    Page<ProdutoEntity> buscarPorNome(String nome, Pageable pageable);
    Page<ProdutoEntity> buscarPorDescricao(String descricao, Pageable pageable);
    Page<ProdutoEntity> buscarPorCategoria(String categoria, Pageable pageable);

}
