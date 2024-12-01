package br.com.ms.produto.application.usecases;

import br.com.ms.produto.application.dto.ProdutoDTO;
import br.com.ms.produto.application.exceptions.ProdutoException;
import br.com.ms.produto.domain.entities.ProdutoEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ValidaProdutoTest {

    private final ValidaProduto validaProduto = new ValidaProduto();

    @Nested
    public class ValidandoProduto {

        @Test
        @DisplayName("Deve validar produto corretamente")
        public void deveValidarProdutoCorretamente() {
            ProdutoDTO produtoDTO = new ProdutoDTO(null, "nome", "descricao", 1.1, 1, "categoria");
            ProdutoEntity produtoEntity = validaProduto.run(produtoDTO);
            assertThat(produtoEntity).isInstanceOf(ProdutoEntity.class).isNotNull();
        }

        @Test
        @DisplayName("Deve lançar exceção para produto inválido")
        public void deveLancarExcecaoParaProdutoInvalido() {
            ProdutoDTO produtoDTO = new ProdutoDTO(null, "", "descricao", 1.1, 1, "categoria");
            assertThatThrownBy(() -> validaProduto.run(produtoDTO))
                    .isInstanceOf(ProdutoException.class)
                    .hasMessage("erro ao validar produto: nome inválido");
        }

    }

}
