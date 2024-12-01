package br.com.ms.produto.application.usecases;

import br.com.ms.produto.application.dto.ProdutoDTO;
import br.com.ms.produto.application.exceptions.ProdutoException;
import br.com.ms.produto.domain.entities.ProdutoEntity;
import org.springframework.stereotype.Service;

@Service
public class ValidaProduto {

    public ProdutoEntity run(ProdutoDTO dto) {
        ProdutoEntity entity;
        try {
            entity = new ProdutoEntity(dto.getNome(),
                    dto.getDescricao(),
                    dto.getPreco(),
                    dto.getQuantidade(),
                    dto.getCategoria());
        } catch (IllegalArgumentException exception) {
            throw new ProdutoException("erro ao validar produto: " + exception.getMessage());
        }
        return entity;
    }

}
