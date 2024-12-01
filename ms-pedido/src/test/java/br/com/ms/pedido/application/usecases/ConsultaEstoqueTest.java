package br.com.ms.pedido.application.usecases;

import br.com.ms.pedido.application.exceptions.PedidoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ConsultaEstoqueTest {

    private ConsultaEstoque consultaEstoque = new ConsultaEstoque();

    @Nested
    public class ConsultandoEstoque {

        @Test
        @DisplayName("deve lancar excecao para produto sem estoque suficiente")
        public void deveLancarExcecaoParaProdutoSemEstoqueSuficiente() {
            assertThatThrownBy(() -> consultaEstoque.run(1, 2))
                    .isInstanceOf(PedidoException.class)
                    .hasMessage("erro ao confirmar pedido, um dos produtos não possui quantidade requerida em estoque");
        }

        @Test
        @DisplayName("deve validar quantidade em estoque corretamente")
        public void deveValidarQuantidadeEmEstoqueCorretamente() {
            consultaEstoque.run(2, 2);
        }

    }

}
