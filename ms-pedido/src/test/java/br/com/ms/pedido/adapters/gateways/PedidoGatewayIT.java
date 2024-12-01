package br.com.ms.pedido.adapters.gateways;

import br.com.ms.pedido.domain.entities.PedidoEntity;
import br.com.ms.pedido.domain.enums.StatusPedido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase
public class PedidoGatewayIT {

    @Autowired
    private PedidoGatewayimpl pedidoGateway;

    private PedidoEntity pedidoEntitySetup;

    @BeforeEach
    public void setUp() {
        pedidoEntitySetup = pedidoGateway.salvar(new PedidoEntity(1L, StatusPedido.EM_ABERTO, Map.of(1L, 1)));
    }

    @Nested
    public class SalvandoPedido {

        @Test
        @DisplayName("Deve salvar pedido corretamente")
        public void deveSalvarPedidoCorretamente() {
            PedidoEntity pedidoEntity = new PedidoEntity(1L, StatusPedido.EM_ABERTO, Map.of(1L, 1, 2L, 2));
            PedidoEntity pedidoEntityCreated = pedidoGateway.salvar(pedidoEntity);
            assertThat(pedidoEntityCreated).isInstanceOf(PedidoEntity.class).isNotNull();
        }

    }

    @Nested
    public class AtualizandoPedido {

        @Test
        @DisplayName("Deve lançar exceçâo para pedido inexistente")
        public void deveLancarExcecaoParaPedidoInexistente() {
            PedidoEntity pedidoEntity = new PedidoEntity(1L, StatusPedido.EM_ABERTO, Map.of(1L, 1, 2L, 2));
            assertThatThrownBy(() -> pedidoGateway.atualizar(0L, pedidoEntity))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("pedido não encontrado");
        }

        @Test
        @DisplayName("Deve atualizar pedido corretamente")
        public void deveAtualizarPedidoCorretamente() {
            PedidoEntity pedidoEntity = new PedidoEntity(1L, StatusPedido.EM_ABERTO, Map.of(1L, 1, 2L, 2));
            PedidoEntity pedidoEntityUpdated = pedidoGateway.atualizar(pedidoEntitySetup.getClienteId(), pedidoEntity);
            assertThat(pedidoEntityUpdated).isInstanceOf(PedidoEntity.class).isNotNull();
        }

    }

    @Nested
    public class BuscandoEntregas {

        @Test
        @DisplayName("Deve buscar pedido por id")
        public void deveBuscarPedidoPorId() {
            PedidoEntity pedidoEntity = pedidoGateway.buscarPorId(pedidoEntitySetup.getClienteId());
            assertThat(pedidoEntity).isInstanceOf(PedidoEntity.class).isNotNull();
        }

        @Test
        @DisplayName("Deve lancar exception para pedido inexistente")
        public void deveLancarExceptionParaPedidoInexistente() {
            assertThatThrownBy(() -> pedidoGateway.buscarPorId(-1L))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("pedido não encontrado");
        }

    }

}
