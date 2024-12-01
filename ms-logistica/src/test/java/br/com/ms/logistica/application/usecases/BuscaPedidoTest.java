package br.com.ms.logistica.application.usecases;

import br.com.ms.logistica.application.dto.PedidoDTO;
import br.com.ms.logistica.application.gateways.PedidoGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@Service
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
        @DisplayName("deve lancar excecao para pedido não encontrado")
        public void deveLancarExcecaoParaPedidoNaoEncontrado() {
            when(pedidoGateway.buscaPorId(1L)).thenReturn(null);
            assertThatThrownBy(() -> buscaPedido.run(1L))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("pedido não encontrado");
        }

        @Test
        @DisplayName("deve buscar pedido corretamente")
        public void deveBuscarPedidoCorretamente() {
            PedidoDTO pedidoDTO = new PedidoDTO(1L, 1L, "CONFIRMADO", null);
            when(pedidoGateway.buscaPorId(1L)).thenReturn(pedidoDTO);
            buscaPedido.run(1L);
            verify(pedidoGateway, times(1)).buscaPorId(1L);
        }

    }

}
