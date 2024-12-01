package br.com.ms.pedido.domain.enums;

import br.com.ms.pedido.domain.enums.StatusPedido;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class StatusPedidoTest {

    @Nested
    public class Validacao {

        @Test
        @DisplayName("deve lançar exceção para string inválidas")
        public void deveLancarExcecaoParaStringInvalidas() {
            assertThatThrownBy(() -> StatusPedido.get("TESTE"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("status inválido");
        }

        @Test
        @DisplayName("deve retornar enum corretamente")
        public void deveRetornarEnumCorretamente() {
            assertThat(StatusPedido.get("EM_ABERTO")).isEqualTo(StatusPedido.EM_ABERTO);
            assertThat(StatusPedido.get("CONFIRMADO")).isEqualTo(StatusPedido.CONFIRMADO);
            assertThat(StatusPedido.get("CONCLUIDO")).isEqualTo(StatusPedido.CONCLUIDO);
            assertThat(StatusPedido.get("CANCELADO")).isEqualTo(StatusPedido.CANCELADO);
        }

    }

}
