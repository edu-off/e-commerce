package br.com.ms.produto.adapters.controllers;

import br.com.ms.produto.adapters.presenters.ProdutoPresenter;
import br.com.ms.produto.application.dto.ProdutoDTO;
import br.com.ms.produto.application.usecases.BuscaProduto;
import br.com.ms.produto.domain.entities.ProdutoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class BuscaProdutoControllerTest {

    @Mock
    private BuscaProduto buscaProduto;

    @Mock
    private ProdutoPresenter presenter;

    @InjectMocks
    private BuscaProdutoController buscaProdutoController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class Buscas {

        @Test
        @DisplayName("deve buscar produto por id")
        public void deveBuscarProdutoPorId() {
            ProdutoDTO produtoDTO = new ProdutoDTO(null, "nome", "descricao", 1.1, 1, "categoria");
            ProdutoEntity produtoEntity = new ProdutoEntity("nome", "descricao", 1.1, 1, "categoria");
            when(buscaProduto.porId(1L)).thenReturn(produtoEntity);
            when(presenter.transform(produtoEntity)).thenReturn(produtoDTO);
            ProdutoDTO produtoDTORetrieved = buscaProdutoController.porId(1L);
            assertThat(produtoDTORetrieved).isInstanceOf(ProdutoDTO.class).isNotNull();

        }

        @Test
        @DisplayName("deve buscar produtos por nome")
        public void deveBuscarProdutosPorNome() {
            Pageable pageable = PageRequest.of(0, 10);
            Page<ProdutoDTO> produtosDTO = Page.empty();
            Page<ProdutoEntity> produtosEntity = Page.empty();
            when(buscaProduto.porNome("nome", pageable)).thenReturn(produtosEntity);
            when(presenter.transform(produtosEntity, pageable)).thenReturn(produtosDTO);
            Page<ProdutoDTO> produtosDTORetrieved = buscaProdutoController.porNome("nome", pageable);
            assertThat(produtosDTORetrieved).isInstanceOf(Page.class).isEmpty();
        }


        @Test
        @DisplayName("deve buscar produtos por descricao")
        public void deveBuscarProdutosPorDescricao() {
            Pageable pageable = PageRequest.of(0, 10);
            Page<ProdutoDTO> produtosDTO = Page.empty();
            Page<ProdutoEntity> produtosEntity = Page.empty();
            when(buscaProduto.porDescricao("descricao", pageable)).thenReturn(produtosEntity);
            when(presenter.transform(produtosEntity, pageable)).thenReturn(produtosDTO);
            Page<ProdutoDTO> produtosDTORetrieved = buscaProdutoController.porDescricao("descricao", pageable);
            assertThat(produtosDTORetrieved).isInstanceOf(Page.class).isEmpty();
        }

        @Test
        @DisplayName("deve buscar produtos por categoria")
        public void deveBuscarProdutosPorCategoria() {
            Pageable pageable = PageRequest.of(0, 10);
            Page<ProdutoDTO> produtosDTO = Page.empty();
            Page<ProdutoEntity> produtosEntity = Page.empty();
            when(buscaProduto.porCategoria("categoria", pageable)).thenReturn(produtosEntity);
            when(presenter.transform(produtosEntity, pageable)).thenReturn(produtosDTO);
            Page<ProdutoDTO> produtosDTORetrieved = buscaProdutoController.porCategoria("categoria", pageable);
            assertThat(produtosDTORetrieved).isInstanceOf(Page.class).isEmpty();
        }

    }

}
