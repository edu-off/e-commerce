package br.com.ms.pedido.application.usecases;

import br.com.ms.pedido.application.gateways.ProdutoGateway;
import br.com.ms.pedido.domain.entities.PedidoEntity;
import br.com.ms.pedido.domain.enums.StatusPedido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.mockito.Mockito.*;

public class DecrementaEstoqueTest {

    @Mock
    private ProdutoGateway produtoGateway;

    @InjectMocks
    private DecrementaEstoque decrementaEstoque;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class DecrementandoEstoque {

        @Test
        @DisplayName("Deve decrementar estoque corretamente")
        public void deveDecrementarEstoqueCorretamente() {
            Map<Long, Integer> produtos = Map.of(1L, 1, 2L, 2);
            PedidoEntity pedidoEntity = new PedidoEntity(1L, StatusPedido.EM_ABERTO, produtos);
            doNothing().when(produtoGateway).decrementaEstoque(anyLong(), anyInt());
            decrementaEstoque.run(pedidoEntity);
            verify(produtoGateway, times(2)).decrementaEstoque(anyLong(), anyInt());
        }

    }

}
