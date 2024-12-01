package br.com.ms.pedido.application.usecases;

import br.com.ms.pedido.application.gateways.PedidoGateway;
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

public class BuscaPedidoTest {

    @Mock
    private PedidoGateway pedidoGateway;

    @InjectMocks
    private BuscaPedido buscaPedido;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class BuscandoPedido {

        @Test
        @DisplayName("Deve buscar pedido corretamente")
        public void deveBuscarPedidoCorretamente() {
            Map<Long, Integer> produtos = Map.of(1L, 1, 2L, 2);
            PedidoEntity pedidoEntity = new PedidoEntity(1L, StatusPedido.EM_ABERTO, produtos);
            when(pedidoGateway.buscarPorId(1L)).thenReturn(pedidoEntity);
            buscaPedido.run(1L);
            verify(pedidoGateway, times(1)).buscarPorId(1L);
        }

    }

}
