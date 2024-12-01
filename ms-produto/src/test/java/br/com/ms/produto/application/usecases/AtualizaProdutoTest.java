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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class AtualizaProdutoTest {

    @Mock
    private ProdutoGateway produtoGateway;

    @InjectMocks
    private AtualizaProduto atualizaProduto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class AtualizandoProduto {

        @Test
        @DisplayName("Deve atualizar produto corretamente")
        public void deveAtualizarProdutoCorretamente() {
            ProdutoEntity produtoEntity = new ProdutoEntity("nome", "descricao", 1.1, 1, "categoria");
            when(produtoGateway.atualizar(1L, produtoEntity)).thenReturn(produtoEntity);
            ProdutoEntity produtoEntityUpdated = atualizaProduto.run(1L, produtoEntity);
            assertThat(produtoEntityUpdated).isInstanceOf(ProdutoEntity.class).isNotNull().isEqualTo(produtoEntity);
        }

    }

}
