package br.com.ms.pedido.application.usecases;

import br.com.ms.pedido.application.dto.PedidoDTO;
import br.com.ms.pedido.application.dto.ProdutoDTO;
import br.com.ms.pedido.application.exceptions.PedidoException;
import br.com.ms.pedido.domain.entities.PedidoEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ValidaPedidoTest {

    private final ValidaPedido validaPedido = new ValidaPedido();

    @Nested
    public class ValidandoPedido {

        @Test
        @DisplayName("Deve validar pedido corretamente")
        public void deveValidarPedidCorretamente() {
            ProdutoDTO produto1 = new ProdutoDTO(1L, 1, 0.0);
            ProdutoDTO produto2 = new ProdutoDTO(2L, 2, 0.0);
            PedidoDTO pedidoDTO = new PedidoDTO(null, 1L, "EM_ABERTO", List.of(produto1, produto2));
            PedidoEntity pedidoEntity = validaPedido.run(pedidoDTO);
            assertThat(pedidoEntity).isInstanceOf(PedidoEntity.class).isNotNull();
        }

        @Test
        @DisplayName("Deve lançar exceção para pedido inválido")
        public void deveLancarExcecaoParaPedidoInvalido() {
            ProdutoDTO produto1 = new ProdutoDTO(1L, 1, 0.0);
            ProdutoDTO produto2 = new ProdutoDTO(2L, 2, 0.0);
            PedidoDTO pedidoDTO = new PedidoDTO(null, 1L, "", List.of(produto1, produto2));
            assertThatThrownBy(() -> validaPedido.run(pedidoDTO))
                    .isInstanceOf(PedidoException.class)
                    .hasMessage("erro ao validar pedido: status inválido");
        }

    }

}
