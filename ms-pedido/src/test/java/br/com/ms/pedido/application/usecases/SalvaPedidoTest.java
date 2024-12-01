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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class SalvaPedidoTest {

    @Mock
    private PedidoGateway pedidoGateway;

    @InjectMocks
    private SalvaPedido salvaPedido;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class SalvandoPedido {

        @Test
        @DisplayName("Deve salvar pedido corretamente")
        public void deveSalvarPedidoCorretamente() {
            Map<Long, Integer> produtos = Map.of(1L, 1, 2L, 2);
            PedidoEntity pedidoEntity = new PedidoEntity(1L, StatusPedido.EM_ABERTO, produtos);
            when(pedidoGateway.salvar(pedidoEntity)).thenReturn(pedidoEntity);
            PedidoEntity pedidoEntityCreated = salvaPedido.run(pedidoEntity);
            assertThat(pedidoEntityCreated).isInstanceOf(PedidoEntity.class).isNotNull().isEqualTo(pedidoEntity);
        }

    }

}
