package br.com.ms.produto.adapters.controllers;

import br.com.ms.produto.adapters.presenters.ProdutoPresenter;
import br.com.ms.produto.application.dto.ProdutoDTO;
import br.com.ms.produto.application.usecases.AtualizaProduto;
import br.com.ms.produto.application.usecases.BuscaProduto;
import br.com.ms.produto.application.usecases.ValidaProduto;
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

public class AtualizaProdutoControllerTest {

    @Mock
    private BuscaProduto buscaProduto;

    @Mock
    private ValidaProduto validaProduto;

    @Mock
    private AtualizaProduto atualizaProduto;

    @Mock
    private ProdutoPresenter presenter;

    @InjectMocks
    private AtualizaProdutoController atualizaProdutoController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class Atualizacao {

        @Test
        @DisplayName("Deve lançar exceção para produto inexistente")
        public void deveLancarExcecaoParaProdutoInexistente() {
            ProdutoDTO produtoDTO = new ProdutoDTO(0L, "nome", "descricao", 1.1, 1, "categoria");
            when(buscaProduto.porId(0L)).thenThrow(new NoSuchElementException("produto não encontrado"));
            assertThatThrownBy(() -> atualizaProdutoController.run(0L, produtoDTO))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("produto não encontrado");
        }

        @Test
        @DisplayName("Deve atualizar produto")
        public void deveAtualizarProduto() {
            ProdutoDTO produtoDTO = new ProdutoDTO(1L, "nome", "descricao", 1.1, 1, "categoria");
            ProdutoEntity produtoEntity = new ProdutoEntity("nome", "descricao", 1.1, 1, "categoria");
            when(buscaProduto.porId(1L)).thenReturn(produtoEntity);
            when(validaProduto.run(produtoDTO)).thenReturn(produtoEntity);
            when(atualizaProduto.run(1L, produtoEntity)).thenReturn(produtoEntity);
            when(presenter.transform(produtoEntity)).thenReturn(produtoDTO);
            ProdutoDTO produtoDTOUpdated = atualizaProdutoController.run(1L, produtoDTO);
            assertThat(produtoDTOUpdated).isInstanceOf(ProdutoDTO.class).isNotNull();
        }

    }

}
