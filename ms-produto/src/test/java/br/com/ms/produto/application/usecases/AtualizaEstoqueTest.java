package br.com.ms.produto.application.usecases;

import br.com.ms.produto.domain.entities.ProdutoEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AtualizaEstoqueTest {

    private AtualizaEstoque atualizaEstoque =  new AtualizaEstoque();

    @Nested
    public class AtualizandoEstoqueProduto {

        @Test
        @DisplayName("Deve incrementar estoque do produto corretamente")
        public void deveIncrementarEstoqueDoProdutoCorretamente() {
            ProdutoEntity produtoEntity = new ProdutoEntity("nome", "descricao", 1.1, 1, "categoria");
            ProdutoEntity produtoEntityUpdated = atualizaEstoque.incrementa(produtoEntity, 1);
            assertThat(produtoEntityUpdated).isInstanceOf(ProdutoEntity.class).isNotNull();
            assertThat(produtoEntityUpdated.getQuantidade()).isEqualTo(2);
        }

        @Test
        @DisplayName("Deve lancar excecao para quantidade superior que o suportado no incremento")
        public void deveLancarExcecaoParaQuantidadeSuperiorAoSuportadoNoIncremento() {
            ProdutoEntity produtoEntity = new ProdutoEntity("nome", "descricao", 1.1, 1, "categoria");
            assertThatThrownBy(() -> atualizaEstoque.incrementa(produtoEntity, 10000000))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("quantidade é superior ao limite suportado");
        }

        @Test
        @DisplayName("Deve lancar excecao para somatoria de quantidade superior que o suportado no incremento")
        public void deveLancarExcecaoParaSomatoriaDeQuantidadeSuperiorAoSuportadoNoIncremento() {
            ProdutoEntity produtoEntity = new ProdutoEntity("nome", "descricao", 1.1, 9999999, "categoria");
            assertThatThrownBy(() -> atualizaEstoque.incrementa(produtoEntity, 1))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("estoque não pode estourar 9.999.999 itens");
        }

        @Test
        @DisplayName("Deve decrementar estoque do produto corretamente")
        public void deveDecrementarEstoqueDoProdutoCorretamente() {
            ProdutoEntity produtoEntity = new ProdutoEntity("nome", "descricao", 1.1, 1, "categoria");
            ProdutoEntity produtoEntityUpdated = atualizaEstoque.decrementa(produtoEntity, 1);
            assertThat(produtoEntityUpdated).isInstanceOf(ProdutoEntity.class).isNotNull();
            assertThat(produtoEntityUpdated.getQuantidade()).isEqualTo(0);
        }

        @Test
        @DisplayName("Deve lançar exceção para quantidade inferior ao que deve ser decrementado no produto")
        public void deveLancarExcecaoParaQuantidadeInferiorAoQueDeveSerDecrementadoNoProduto() {
            ProdutoEntity produtoEntity = new ProdutoEntity("nome", "descricao", 1.1, 1, "categoria");
            assertThatThrownBy(() -> atualizaEstoque.decrementa(produtoEntity, 2))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("quantidade a ser subtraida é superior a quantidade existente");
        }


    }

}
