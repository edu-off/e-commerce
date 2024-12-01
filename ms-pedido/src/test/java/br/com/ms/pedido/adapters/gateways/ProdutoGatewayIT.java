package br.com.ms.pedido.adapters.gateways;

import br.com.ms.pedido.application.dto.ProdutoDTO;
import br.com.ms.pedido.application.gateways.ProdutoGateway;
import br.com.ms.pedido.config.StubConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@AutoConfigureWireMock(port = 0)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProdutoGatewayIT {

    @Autowired
    private ProdutoGateway produtoGateway;

    @Nested
    public class BuscandoProduto {

        @Test
        @DisplayName("deve buscar produto corretamente")
        public void deveRegistrarEntregaCorretamente() throws IOException {
            StubConfig.recuperaProdutoMockResponseOK(1L, 1, 0.0);
            ProdutoDTO produtoDTO = produtoGateway.buscaProduto(1L);
            assertThat(produtoDTO).isInstanceOf(ProdutoDTO.class).isNotNull();
        }

        @Test
        @DisplayName("deve retornar bad request para tentativa de registro de entrega")
        public void deveRetornarBadRequestParaTentativaDeRegistroDeEntrega() throws IOException {
            StubConfig.recuperaProdutoMockResponseNotFound(1L);
            assertThatThrownBy(() -> produtoGateway.buscaProduto(1L))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("not found");
        }

    }

    @Nested
    public class AtualizandoEstoqueDeProdutos {

        @Test
        @DisplayName("deve incrementar produto corretamente")
        public void deveIncrementarProdutoCorretamente() throws IOException {
            StubConfig.incrementaEstoqueMockResponseOK(1L, 1);
            produtoGateway.incrementaEstoque(1L, 1);
        }

        @Test
        @DisplayName("deve retornar not found para tentativa de incremento de estoque")
        public void deveRetornarNotFoundParaTentativaDeIncrementoDeEstoque() throws IOException {
            StubConfig.incrementaEstoqueMockResponseNotFound(1L, 1);
            assertThatThrownBy(() -> produtoGateway.incrementaEstoque(1L, 1))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("not found");
        }

        @Test
        @DisplayName("deve decrementar produto corretamente")
        public void deveDecrementarProdutoCorretamente() throws IOException {
            StubConfig.decrementaEstoqueMockResponseOK(1L, 1);
            produtoGateway.decrementaEstoque(1L, 1);
        }

        @Test
        @DisplayName("deve retornar not found para tentativa de decremento de estoque")
        public void deveRetornarNotFoundParaTentativaDeDecrementoDeEstoque() throws IOException {
            StubConfig.decrementaEstoqueMockResponseNotFound(1L, 1);
            assertThatThrownBy(() -> produtoGateway.decrementaEstoque(1L, 1))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("not found");
        }


    }

}
