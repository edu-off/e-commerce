package br.com.ms.logistica.domain.entities;

import br.com.ms.logistica.domain.enums.StatusEntrega;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class EntregaEntityTest {

    private final StatusEntrega status = StatusEntrega.PENDENTE;
    private final Long pedidoId = 1L;
    private final Long clienteId = 1L;

    @Nested
    public class ValidacaoConstrutor {

        @Test
        @DisplayName("Deve instanciar Entrega corretamente")
        public void deveInstanciarEntregaCorretamente() {
            EntregaEntity entrega = new EntregaEntity(status, pedidoId, clienteId);
            assertThat(entrega).isInstanceOf(EntregaEntity.class).isNotNull();
            assertThat(entrega.getStatus()).isEqualTo(status);
            assertThat(entrega.getPedidoId()).isEqualTo(pedidoId);
            assertThat(entrega.getClienteId()).isEqualTo(clienteId);
        }

    }

    @Nested
    public class ValidacaoStatus {

        @Test
        @DisplayName("Deve lançar exceção para status nulo")
        public void deveLancarExcecaoParaStatusNulo() {
            StatusEntrega statusNulo = null;
            assertThatThrownBy(() -> new EntregaEntity(statusNulo, pedidoId, clienteId))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("status da entrega inválido");
        }

    }


    @Nested
    public class ValidacaoPedidoId {

        @Test
        @DisplayName("Deve lançar exceção para pedido id nulo")
        public void deveLancarExcecaoParaPedidoIdNulo() {
            Long pedidoIdNulo = null;
            assertThatThrownBy(() -> new EntregaEntity(status, pedidoIdNulo, clienteId))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("identificador do pedido inválido");
        }

    }

    @Nested
    public class ValidacaoClienteId {

        @Test
        @DisplayName("Deve lançar exceção para cliente id nulo")
        public void deveLancarExcecaoParaClienteIdNulo() {
            Long clienteIdNulo = null;
            assertThatThrownBy(() -> new EntregaEntity(status, pedidoId, clienteIdNulo))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("identificador do cliente inválido");
        }

    }

}
