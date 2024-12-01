package br.com.ms.pedido.application.usecases;

import br.com.ms.pedido.application.dto.ProdutoDTO;
import br.com.ms.pedido.application.gateways.PedidoGateway;
import br.com.ms.pedido.application.gateways.ProdutoGateway;
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
import static org.mockito.Mockito.*;

public class BuscaProdutoTest {

    @Mock
    private ProdutoGateway produtoGateway;

    @InjectMocks
    private BuscaProduto buscaProduto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class BuscandoPedido {

        @Test
        @DisplayName("Deve buscar pedido corretamente")
        public void deveBuscarPedidoCorretamente() {
            ProdutoDTO produtoDTO = new ProdutoDTO(1L, 1, 0.0);
            when(produtoGateway.buscaProduto(1L)).thenReturn(produtoDTO);
            ProdutoDTO produtoDTOReturned = buscaProduto.run(1L);
            assertThat(produtoDTOReturned).isInstanceOf(ProdutoDTO.class).isNotNull();
        }

    }

}
