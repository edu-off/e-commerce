package br.com.ms.pedido.application.usecases;

import br.com.ms.pedido.application.dto.ProdutoDTO;
import br.com.ms.pedido.domain.entities.PedidoEntity;
import br.com.ms.pedido.domain.enums.StatusPedido;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AtualizaListProdutosTest {

    private final AtualizaListaProdutos atualizaListaProdutos = new AtualizaListaProdutos();

    @Nested
    public class AtualizandoPedido {

        @Test
        @DisplayName("Deve atualizar lista de produtos do pedido com pedido sem produtos")
        public void deveAtaulizarListaDeProdutosDoPedidoComPedidoSemProdutos() {
            ProdutoDTO produto1 = new ProdutoDTO(1L, 1, 0.0);
            ProdutoDTO produto2 = new ProdutoDTO(2L, 2, 0.0);
            List<ProdutoDTO> produtos = List.of(produto1, produto2);
            PedidoEntity pedidoEntity = new PedidoEntity(1L, StatusPedido.EM_ABERTO, Map.of(3L, 3, 4L, 4));
            PedidoEntity pedidoEntityUpdated = atualizaListaProdutos.run(pedidoEntity, produtos);
            assertThat(pedidoEntityUpdated).isInstanceOf(PedidoEntity.class).isNotNull();
            assertThat(pedidoEntityUpdated.getProdutos()).hasSize(4);
        }

        @Test
        @DisplayName("Deve atualizar lista de produtos do pedido com pedido com produtos")
        public void deveAtaulizarListaDeProdutosDoPedidoComPedidoComProdutos() {
            ProdutoDTO produto1 = new ProdutoDTO(1L, 1, 0.0);
            ProdutoDTO produto2 = new ProdutoDTO(2L, 2, 0.0);
            List<ProdutoDTO> produtos = List.of(produto1, produto2);
            PedidoEntity pedidoEntity = new PedidoEntity(1L, StatusPedido.EM_ABERTO, Map.of(1L, 2, 2L, 4));
            PedidoEntity pedidoEntityUpdated = atualizaListaProdutos.run(pedidoEntity, produtos);
            assertThat(pedidoEntityUpdated).isInstanceOf(PedidoEntity.class).isNotNull();
            assertThat(pedidoEntityUpdated.getProdutos()).hasSize(2);
        }

    }

}
