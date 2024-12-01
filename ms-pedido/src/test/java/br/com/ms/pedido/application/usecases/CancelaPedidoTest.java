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

public class CancelaPedidoTest {

    @Mock
    private PedidoGateway pedidoGateway;

    @InjectMocks
    private CancelaPedido cancelaPedido;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class CancelandoPedido {

        @Test
        @DisplayName("Deve cancelar pedido corretamente")
        public void deveCancelarPedidoCorretamente() {
            Map<Long, Integer> produtos = Map.of(1L, 1, 2L, 2);
            PedidoEntity pedidoEntity = new PedidoEntity(1L, StatusPedido.EM_ABERTO, produtos);
            when(pedidoGateway.atualizar(1L, pedidoEntity)).thenReturn(pedidoEntity);
            cancelaPedido.run(1L, pedidoEntity);
            verify(pedidoGateway, times(1)).atualizar(1L, pedidoEntity);
        }

    }

}
