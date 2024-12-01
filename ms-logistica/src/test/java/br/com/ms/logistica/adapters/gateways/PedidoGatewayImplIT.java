package br.com.ms.logistica.adapters.gateways;

import br.com.ms.logistica.application.gateways.PedidoGateway;
import br.com.ms.logistica.config.StubConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@AutoConfigureWireMock(port = 0)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PedidoGatewayImplIT {

    @Autowired
    public PedidoGateway pedidoGateway;

    public Long id = 1L;

    @Nested
    public class ConcluindoPedido {

        @Test
        @DisplayName("deve concluir pedido corretamente")
        public void deveConcluirPedidoCorretamente() throws IOException {
            StubConfig.concluiPedidoMockResponseOK(id);
            pedidoGateway.concluiPedido(id);
        }

        @Test
        @DisplayName("deve retornar not found para pedido inexistente")
        public void deveRetornarNotFoundParaPedidoInexistente() throws IOException {
            StubConfig.concluiPedidoMockResponseNotFound(id);
            assertThatThrownBy(() -> pedidoGateway.concluiPedido(id)).isInstanceOf(NoSuchElementException.class);
        }

    }

    @Nested
    public class CancelandoPedido {

        @Test
        @DisplayName("deve cancelar pedido corretamente")
        public void deveCancelarPedidoCorretamente() throws IOException {
            StubConfig.cancelaPedidoMockResponseOK(id);
            pedidoGateway.cancelaPedido(id);
        }

        @Test
        @DisplayName("deve retornar not found para pedido inexistente")
        public void deveRetornarNotFoundParaPedidoInexistente() throws IOException {
            StubConfig.cancelaPedidoMockResponseNotFound(id);
            assertThatThrownBy(() -> pedidoGateway.cancelaPedido(id)).isInstanceOf(NoSuchElementException.class);
        }

    }


}
