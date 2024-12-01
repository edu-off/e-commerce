package br.com.ms.logistica.application.usecases;

import br.com.ms.logistica.application.gateways.PedidoGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.stereotype.Service;

import static org.mockito.Mockito.*;

@Service
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
        @DisplayName("deve cancelar pedido corretamente")
        public void deveCancelarPedidoCorretamente() {
            doNothing().when(pedidoGateway).cancelaPedido(1L);
            cancelaPedido.run(1L);
            verify(pedidoGateway, times(1)).cancelaPedido(1L);
       }

    }

}
