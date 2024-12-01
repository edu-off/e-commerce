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
import static org.mockito.Mockito.*;

public class RemoveProdutoTest {

    @Mock
    private ProdutoGateway produtoGateway;

    @InjectMocks
    private RemoveProduto removeProduto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class AtualizandoProduto {

        @Test
        @DisplayName("Deve remover produto corretamente")
        public void deveRemoverProdutoCorretamente() {
            doNothing().when(produtoGateway).remover(1L);
            removeProduto.run(1L);
            verify(produtoGateway, times(1)).remover(1L);
        }

    }

}
