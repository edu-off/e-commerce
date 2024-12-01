package br.com.ms.pedido.application.usecases;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AtualizaPrecoPedidoTest {

    private AtualizaPrecoPedido atualizaPrecoPedido = new AtualizaPrecoPedido();

    @Nested
    public class AtualizandoPrecoPedido {

        @Test
        @DisplayName("deve atualizar preco do pedido corretamente")
        public void deveAtualizarPrecoDoPedidoCorretamente() {
            assertThat(atualizaPrecoPedido.run(2, 10.20))
                    .isInstanceOf(Double.class)
                    .isNotNull()
                    .isEqualTo(20.40);
        }

    }

}
