package br.com.ms.produto.adapters.controllers;

import br.com.ms.produto.adapters.presenters.ProdutoPresenter;
import br.com.ms.produto.application.dto.ProdutoDTO;
import br.com.ms.produto.application.usecases.AtualizaEstoque;
import br.com.ms.produto.application.usecases.AtualizaProduto;
import br.com.ms.produto.application.usecases.BuscaProduto;
import br.com.ms.produto.domain.entities.ProdutoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

public class AtualizaEstoqueControllerTest {

    @Mock
    private BuscaProduto buscaProduto;

    @Mock
    private AtualizaEstoque atualizaEstoque;

    @Mock
    private AtualizaProduto atualizaProduto;

    @Mock
    private ProdutoPresenter presenter;

    @InjectMocks
    private AtualizaEstoqueController atualizaEstoqueController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class Atualizacao {

        @Test
        @DisplayName("Deve lançar exceção para produto inexistente durante incremento de estoque")
        public void deveLancarExcecaoParaProdutoInexistenteDuranteIncrementoDeEstoque() {
            when(buscaProduto.porId(0L)).thenThrow(new NoSuchElementException("produto não encontrado"));
            assertThatThrownBy(() -> atualizaEstoqueController.incrementa(0L, 1))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("produto não encontrado");
        }

        @Test
        @DisplayName("Deve lançar exceção para produto inexistente durante decremento de estoque")
        public void deveLancarExcecaoParaProdutoInexistenteDuranteDecrementoDeEstoque() {
            when(buscaProduto.porId(0L)).thenThrow(new NoSuchElementException("produto não encontrado"));
            assertThatThrownBy(() -> atualizaEstoqueController.decrementa(0L, 1))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("produto não encontrado");
        }

        @Test
        @DisplayName("Deve incrementar estoque do produto")
        public void deveIncrementarEstoqueDoProduto() {
            ProdutoDTO produtoDTO = new ProdutoDTO(null, "nome", "descricao", 1.1, 1, "categoria");
            ProdutoEntity produtoEntity = new ProdutoEntity("nome", "descricao", 1.1, 1, "categoria");
            when(buscaProduto.porId(1L)).thenReturn(produtoEntity);
            when(atualizaEstoque.incrementa(produtoEntity, 1)).thenReturn(produtoEntity);
            when(atualizaProduto.run(1L, produtoEntity)).thenReturn(produtoEntity);
            when(presenter.transform(produtoEntity)).thenReturn(produtoDTO);
            ProdutoDTO produtoDTOUpdated = atualizaEstoqueController.incrementa(1L, 1);
            assertThat(produtoDTOUpdated).isInstanceOf(ProdutoDTO.class).isNotNull();
        }

        @Test
        @DisplayName("Deve decrementar estoque do produto")
        public void deveDecrementarEstoqueDoProduto() {
            ProdutoDTO produtoDTO = new ProdutoDTO(null, "nome", "descricao", 1.1, 1, "categoria");
            ProdutoEntity produtoEntity = new ProdutoEntity("nome", "descricao", 1.1, 1, "categoria");
            when(buscaProduto.porId(1L)).thenReturn(produtoEntity);
            when(atualizaEstoque.decrementa(produtoEntity, 1)).thenReturn(produtoEntity);
            when(atualizaProduto.run(1L, produtoEntity)).thenReturn(produtoEntity);
            when(presenter.transform(produtoEntity)).thenReturn(produtoDTO);
            ProdutoDTO produtoDTOUpdated = atualizaEstoqueController.decrementa(1L, 1);
            assertThat(produtoDTOUpdated).isInstanceOf(ProdutoDTO.class).isNotNull();
        }

        @Test
        @DisplayName("Deve lancar excecao para decremento de quantidade superior ao existente")
        public void deveLancarExcecaoParaDecrementoDeQuantidadeSuperiorAoExistente() {
            ProdutoEntity produtoEntity = new ProdutoEntity("nome", "descricao", 1.1, 1, "categoria");
            when(buscaProduto.porId(1L)).thenReturn(produtoEntity);
            when(atualizaEstoque.decrementa(produtoEntity, 1)).thenThrow(new IllegalArgumentException("quantidade a ser subtraida é superior a quantidade existente"));
            assertThatThrownBy(() -> atualizaEstoqueController.decrementa(1L, 1))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("quantidade a ser subtraida é superior a quantidade existente");
        }

    }

}
