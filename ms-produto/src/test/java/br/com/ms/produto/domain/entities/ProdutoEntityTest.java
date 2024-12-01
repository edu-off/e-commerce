package br.com.ms.produto.domain.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProdutoEntityTest {

    private String nome = "nome";
    private String descricao = "descricao";
    private Double preco = 1.0;
    private Integer quantidade = 1;
    private String categoria = "categoria";

    @Nested
    public class ValidacaoConstrutor {

        @Test
        @DisplayName("Deve instanciar Produto corretamente")
        public void deveInstanciarProdutoCorretamente() {
            ProdutoEntity produto = new ProdutoEntity(nome, descricao, preco, quantidade, categoria);
            assertThat(produto).isInstanceOf(ProdutoEntity.class).isNotNull();
            assertThat(produto.getNome()).isEqualTo(nome);
            assertThat(produto.getDescricao()).isEqualTo(descricao);
            assertThat(produto.getPreco()).isEqualTo(preco);
            assertThat(produto.getQuantidade()).isEqualTo(quantidade);
            assertThat(produto.getCategoria()).isEqualTo(categoria);
        }

    }

    @Nested
    public class ValidacaoNome {

        @Test
        @DisplayName("Deve lançar exceção para nome nulo")
        public void deveLancarExcecaoParaNomeNulo() {
            String nomeNulo = null;
            assertThatThrownBy(() -> new ProdutoEntity(nomeNulo, descricao, preco, quantidade, categoria))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("nome inválido");
        }

        @Test
        @DisplayName("Deve lançar exceção para nome em branco")
        public void deveLancarExcecaoParaNOmeEmBranco() {
            String nomeEmBranco = "";
            assertThatThrownBy(() ->  new ProdutoEntity(nomeEmBranco, descricao, preco, quantidade, categoria))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("nome inválido");
        }

    }

    @Nested
    public class ValidacaoDescricao {

        @Test
        @DisplayName("Deve lançar exceção para descricao nula")
        public void deveLancarExcecaoParaDescricaoNula() {
            String descricaoNula = null;
            assertThatThrownBy(() -> new ProdutoEntity(nome, descricaoNula, preco, quantidade, categoria))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("descrição inválida");
        }

        @Test
        @DisplayName("Deve lançar exceção para descricao em branco")
        public void deveLancarExcecaoParaDescricaoEmBranco() {
            String descricaoEmBranco = "";
            assertThatThrownBy(() ->  new ProdutoEntity(nome, descricaoEmBranco, preco, quantidade, categoria))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("descrição inválida");
        }

    }

    @Nested
    public class ValidacaoPreco {

        @Test
        @DisplayName("Deve lançar exceção para preco nulo")
        public void deveLancarExcecaoParaPrecoNulo() {
            Double precoNulo = null;
            assertThatThrownBy(() -> new ProdutoEntity(nome, descricao, precoNulo, quantidade, categoria))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("preço inválido");
        }

        @Test
        @DisplayName("Deve lançar exceção para descricao em branco")
        public void deveLancarExcecaoParaDescricaoEmBranco() {
            Double precoZerado = null;
            assertThatThrownBy(() ->  new ProdutoEntity(nome, descricao, precoZerado, quantidade, categoria))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("preço inválido");
        }

    }

    @Nested
    public class ValidacaoQuantidade {

        @Test
        @DisplayName("Deve lançar exceção para quantidade nula")
        public void deveLancarExcecaoParaQuantidadeNula() {
            Integer quantidadeNula = null;
            assertThatThrownBy(() -> new ProdutoEntity(nome, descricao, preco, quantidadeNula, categoria))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("quantidade inválida");
        }

        @Test
        @DisplayName("Deve lançar exceção para quantidade negativa")
        public void deveLancarExcecaoParaQuantidadeNegativa() {
            Integer quantidadeNegativa = -1;
            assertThatThrownBy(() ->  new ProdutoEntity(nome, descricao, preco, quantidadeNegativa, categoria))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("quantidade inválida");
        }

    }

    @Nested
    public class ValidacaoCategoria {

        @Test
        @DisplayName("Deve lançar exceção para categoria nula")
        public void deveLancarExcecaoParaCategoriaNula() {
            String categoriaNula = null;
            assertThatThrownBy(() -> new ProdutoEntity(nome, descricao, preco, quantidade, categoriaNula))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("categoria inválida");
        }

        @Test
        @DisplayName("Deve lançar exceção para categoria em branco")
        public void deveLancarExcecaoParaCategoriaEmBranco() {
            String categoriaEmBranco = "";
            assertThatThrownBy(() ->  new ProdutoEntity(nome, descricao, preco, quantidade, categoriaEmBranco))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("categoria inválida");
        }

    }

}
