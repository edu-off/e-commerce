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
public class ConcluiPedidoTest {

    @Mock
    private PedidoGateway pedidoGateway;

    @InjectMocks
    private ConcluiPedido concluiPedido;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class ConcluindoPedido {

        @Test
        @DisplayName("deve concluir pedido corretamente")
        public void deveConcluirPedidoCorretamente() {
            doNothing().when(pedidoGateway).concluiPedido(1L);
            concluiPedido.run(1L);
            verify(pedidoGateway, times(1)).concluiPedido(1L);
       }

    }

}
