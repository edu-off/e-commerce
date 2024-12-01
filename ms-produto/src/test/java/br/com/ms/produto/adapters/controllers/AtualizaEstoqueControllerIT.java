package br.com.ms.produto.adapters.controllers;

import br.com.ms.produto.application.dto.ProdutoDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
@AutoConfigureTestDatabase
public class AtualizaEstoqueControllerIT {

    @Autowired
    private AtualizaEstoqueController atualizaEstoqueController;

    @Autowired
    private RegistraProdutoController registraProdutoController;

    @Nested
    public class Atualizacao {

        @Test
        @DisplayName("Deve lançar exceção para produto inexistente durante incremento de estoque")
        public void deveLancarExcecaoParaProdutoInexistenteDuranteIncrementoDeEstoque() {
            assertThatThrownBy(() -> atualizaEstoqueController.incrementa(0L, 1))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("produto não encontrado");
        }

        @Test
        @DisplayName("Deve lançar exceção para produto inexistente durante decremento de estoque")
        public void deveLancarExcecaoParaProdutoInexistenteDuranteDecrementoDeEstoque() {
            assertThatThrownBy(() -> atualizaEstoqueController.decrementa(0L, 1))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("produto não encontrado");
        }

        @Test
        @DisplayName("Deve incrementar estoque do produto")
        public void deveIncrementarEstoqueDoProduto() {
            ProdutoDTO produtoDTO = registraProdutoController.run(ProdutoDTO.builder()
                    .nome("nome")
                    .descricao("descricao")
                    .preco(1.1)
                    .quantidade(1)
                    .categoria("categoria")
                    .build());

            atualizaEstoqueController.incrementa(produtoDTO.getId(), 1);
        }

        @Test
        @DisplayName("Deve decrementar estoque do produto")
        public void deveDecrementarEstoqueDoProduto() {
            ProdutoDTO produtoDTO = registraProdutoController.run(ProdutoDTO.builder()
                    .nome("nome")
                    .descricao("descricao")
                    .preco(1.1)
                    .quantidade(1)
                    .categoria("categoria")
                    .build());

            atualizaEstoqueController.decrementa(produtoDTO.getId(), 1);
        }

        @Test
        @DisplayName("Deve lancar excecao para decremento de quantidade superior ao existente")
        public void deveLancarExcecaoParaDecrementoDeQuantidadeSuperiorAoExistente() {
            ProdutoDTO produtoDTO = registraProdutoController.run(ProdutoDTO.builder()
                    .nome("nome")
                    .descricao("descricao")
                    .preco(1.1)
                    .quantidade(1)
                    .categoria("categoria")
                    .build());

            assertThatThrownBy(() -> atualizaEstoqueController.decrementa(produtoDTO.getId(), 2))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("quantidade a ser subtraida é superior a quantidade existente");
        }

    }

}
