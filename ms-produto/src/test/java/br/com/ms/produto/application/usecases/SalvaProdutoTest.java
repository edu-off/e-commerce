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

public class SalvaProdutoTest {

    @Mock
    private ProdutoGateway produtoGateway;

    @InjectMocks
    private SalvaProduto salvaProduto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class SalvandoProduto {

        @Test
        @DisplayName("Deve salvar produto corretamente")
        public void deveSalvarProdutoCorretamente() {
            ProdutoEntity produtoEntity = new ProdutoEntity("nome", "descricacao", 1.1, 1, "categoria");
            when(produtoGateway.salvar(produtoEntity)).thenReturn(produtoEntity);
            ProdutoEntity produtoEntityCreated = salvaProduto.run(produtoEntity);
            assertThat(produtoEntityCreated).isInstanceOf(ProdutoEntity.class).isNotNull().isEqualTo(produtoEntity);
        }

    }

}
