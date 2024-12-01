package br.com.ms.pedido.adapters.gateways;

import br.com.ms.pedido.application.gateways.ClienteGateway;
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

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@AutoConfigureWireMock(port = 0)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClienteGatewayIT {

    @Autowired
    private ClienteGateway clienteGateway;

    @Nested
    public class BuscandoCliente {

        @Test
        @DisplayName("deve busca cliente corretamente")
        public void deveBuscarEntregaCorretamente() throws IOException {
            StubConfig.recuperaClienteMockResponseOK(1L);
            clienteGateway.buscaPorId(1L);
        }

        @Test
        @DisplayName("deve retornar bad request para tentativa de registro de entrega")
        public void deveRetornarBadRequestParaTentativaDeRegistroDeEntrega() throws IOException {
            StubConfig.recuperaClienteMockResponseNotFound(1L);
            assertThatThrownBy(() -> clienteGateway.buscaPorId(1L))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("not found");
        }

    }

}
