package br.com.ms.pedido.domain.entities;

import br.com.ms.pedido.domain.enums.StatusPedido;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PedidoEntityTest {

    private final Long clienteId = 1L;
    private final StatusPedido status = StatusPedido.EM_ABERTO;
    private final Map<Long, Integer> produtos = Map.of(1L, 1);

    @Nested
    public class ValidacaoConstrutor {

        @Test
        @DisplayName("Deve instanciar pedido corretamente")
        public void deveInstanciarPedidoCorretamente() {
            PedidoEntity pedidoEntity = new PedidoEntity(clienteId, status, produtos);
            assertThat(pedidoEntity).isInstanceOf(PedidoEntity.class).isNotNull();
            assertThat(pedidoEntity.getClienteId()).isEqualTo(clienteId);
            assertThat(pedidoEntity.getStatus()).isEqualTo(status);
            assertThat(pedidoEntity.getProdutos()).isInstanceOf(Map.class).isNotNull().hasSize(1);
            assertThat(pedidoEntity.getDataAbertura()).isInstanceOf(LocalDateTime.class).isNotNull();
        }

    }

    @Nested
    public class ValidacaoClienteId {

        @Test
        @DisplayName("Deve lançar exceção para cliente id nulo")
        public void deveLancarExcecaoParaClienteIdNulo() {
            Long clienteIdNulo = null;
            assertThatThrownBy(() -> new PedidoEntity(clienteIdNulo, status, produtos))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("identificador do cliente inválido");
        }

    }

    @Nested
    public class ValidacaoStatus {

        @Test
        @DisplayName("Deve lançar exceção para status nulo")
        public void deveLancarExcecaoParaStatusNulo() {
            StatusPedido statusNulo = null;
            assertThatThrownBy(() -> new PedidoEntity(clienteId, statusNulo, produtos))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("status do pedido inválido");
        }

    }

    @Nested
    public class ValidacaoProdutos {

        @Test
        @DisplayName("Deve lançar exceção para lista de produtos nula")
        public void deveLancarExcecaoParaListaDeProdutosNula() {
            Map<Long, Integer> listaProdutosNula = null;
            assertThatThrownBy(() -> new PedidoEntity(clienteId, status, listaProdutosNula))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("lista de produtos inválida");
        }

        @Test
        @DisplayName("Deve lançar exceção para lista de produtos vazia")
        public void deveLancarExcecaoParaListaDeProdutosVazia() {
            Map<Long, Integer> listaProdutosVazia = new HashMap<>();
            assertThatThrownBy(() -> new PedidoEntity(clienteId, status, listaProdutosVazia))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("pedido sem produtos");
        }

        @Test
        @DisplayName("Deve lançar exceção para lista de produtos com dados inválidos")
        public void deveLancarExcecaoParaListaDeProdutosComDadosInvalidos() {
            Map<Long, Integer> listaProdutosComDadosInvalidos = Map.of(0L, 0);
            assertThatThrownBy(() -> new PedidoEntity(clienteId, status, listaProdutosComDadosInvalidos))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("dados dos produtos inválidos");
        }

    }

}
