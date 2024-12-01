package br.com.ms.logistica.adapters.gateways;

import br.com.ms.logistica.application.dto.ClienteDTO;
import br.com.ms.logistica.application.gateways.ClienteGateway;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@AutoConfigureWireMock(port = 0)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClienteGatewayImplIT {

    @Autowired
    public ClienteGateway clienteGateway;

    public Long id = 1L;

    @Nested
    public class RecuperandoCliente {

        @Test
        @DisplayName("deve recuperar cliente corretamente")
        public void deveRecuperarClienteCorretamente() throws IOException {
            StubConfig.recuperaClienteMockResponseOK(id);
            ClienteDTO clienteDTO = clienteGateway.buscaPorId(id);
            assertThat(clienteDTO).isInstanceOf(ClienteDTO.class).isNotNull();
        }

        @Test
        @DisplayName("deve retornar not found para cliente inexistente")
        public void deveRetornarNotFoundParaClienteInexsitente() throws IOException {
            StubConfig.recuperaClienteMockResponseNotFound(id);
            assertThatThrownBy(() -> clienteGateway.buscaPorId(id)).isInstanceOf(NoSuchElementException.class);
        }

    }

}
