package br.com.ms.pedido.application.usecases;

import br.com.ms.pedido.application.dto.EntregaDTO;
import br.com.ms.pedido.application.gateways.EntregaGateway;
import br.com.ms.pedido.application.gateways.ProdutoGateway;
import br.com.ms.pedido.domain.entities.PedidoEntity;
import br.com.ms.pedido.domain.enums.StatusPedido;
import org.hibernate.mapping.Any;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.AtLeast;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class IncrementaEstoqueTest {

    @Mock
    private ProdutoGateway produtoGateway;

    @InjectMocks
    private IncrementaEstoque incrementaEstoque;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class IncrementandoEstoque {

        @Test
        @DisplayName("Deve incrementar estoque corretamente")
        public void deveIncrementarEstoqueCorretamente() {
            Map<Long, Integer> produtos = Map.of(1L, 1, 2L, 2);
            PedidoEntity pedidoEntity = new PedidoEntity(1L, StatusPedido.EM_ABERTO, produtos);
            doNothing().when(produtoGateway).incrementaEstoque(anyLong(), anyInt());
            incrementaEstoque.run(pedidoEntity);
            verify(produtoGateway, times(2)).incrementaEstoque(anyLong(), anyInt());
        }

    }

}
