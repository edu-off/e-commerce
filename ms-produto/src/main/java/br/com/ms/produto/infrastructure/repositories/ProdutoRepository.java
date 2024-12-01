package br.com.ms.produto.infrastructure.repositories;

import br.com.ms.produto.infrastructure.models.ProdutoModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoModel, Long> {

    Page<ProdutoModel> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
    Page<ProdutoModel> findByDescricaoContainingIgnoreCase(String descricao, Pageable pageable);
    Page<ProdutoModel> findByCategoriaContainingIgnoreCase(String categoria, Pageable pageable);

}
