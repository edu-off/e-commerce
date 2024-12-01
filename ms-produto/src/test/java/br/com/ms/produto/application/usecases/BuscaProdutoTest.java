package br.com.ms.produto.application.usecases;

import br.com.ms.produto.application.gateways.ProdutoGateway;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class BuscaProdutoTest {

    @Mock
    private ProdutoGateway produtoGateway;

    @InjectMocks
    private BuscaProduto buscaProduto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class BuscandoCliente {

        @Test
        @DisplayName("Deve buscar produto por id corretamente")
        public void deveBuscarProdutoPorIdCorretamente() {
            ProdutoEntity produtoEntity = new ProdutoEntity("nome", "descricao", 1.1, 1, "categoria");
            when(produtoGateway.buscarPorId(1L)).thenReturn(produtoEntity);
            ProdutoEntity produtoEntityRetrieved = buscaProduto.porId(1L);
            assertThat(produtoEntityRetrieved).isInstanceOf(ProdutoEntity.class).isNotNull().isEqualTo(produtoEntity);
        }

        @Test
        @DisplayName("Deve buscar produto por nome corretamente")
        public void deveBuscarProdutoPorNomeCorretamente() {
            Page<ProdutoEntity> produtos = Page.empty();
            when(produtoGateway.buscarPorNome("nome", PageRequest.of(0, 10)))
                    .thenReturn(produtos);
            Page<ProdutoEntity> produtosRetrieved = buscaProduto.porNome("nome", PageRequest.of(0, 10));
            assertThat(produtosRetrieved).isInstanceOf(Page.class).isNotNull().isEmpty();
        }

        @Test
        @DisplayName("Deve buscar produto por descricao corretamente")
        public void deveBuscarProdutoPorDescricaoCorretamente() {
            Page<ProdutoEntity> produtos = Page.empty();
            when(produtoGateway.buscarPorDescricao("descricao", PageRequest.of(0, 10)))
                    .thenReturn(produtos);
            Page<ProdutoEntity> produtosRetrieved = buscaProduto.porDescricao("descricao", PageRequest.of(0, 10));
            assertThat(produtosRetrieved).isInstanceOf(Page.class).isNotNull().isEmpty();
        }

        @Test
        @DisplayName("Deve buscar produto por categoria corretamente")
        public void deveBuscarProdutoPorCategoriaCorretamente() {
            Page<ProdutoEntity> produtos = Page.empty();
            when(produtoGateway.buscarPorCategoria("categoria", PageRequest.of(0, 10)))
                    .thenReturn(produtos);
            Page<ProdutoEntity> produtosRetrieved = buscaProduto.porCategoria("categoria", PageRequest.of(0, 10));
            assertThat(produtosRetrieved).isInstanceOf(Page.class).isNotNull().isEmpty();
        }

    }

}
