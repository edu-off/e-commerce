package br.com.ms.pedido.adapters.gateways;

import br.com.ms.pedido.application.dto.EntregaDTO;
import br.com.ms.pedido.application.gateways.EntregaGateway;
import br.com.ms.pedido.config.StubConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@AutoConfigureWireMock(port = 0)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EntregaGatewayIT {

    @Autowired
    private EntregaGateway entregaGateway;

    @Nested
    public class RegistrandoEntrega {

        @Test
        @DisplayName("deve registrar entrega corretamente")
        public void deveRegistrarEntregaCorretamente() throws IOException {
            StubConfig.registraEntregaMockResponseOK(1L, 1L);
            EntregaDTO entregaDTO = new EntregaDTO(null, "PENDENTE", 1L, 1L);
            entregaGateway.registraEntrega(entregaDTO);
        }

        @Test
        @DisplayName("deve retornar bad request para tentativa de registro de entrega")
        public void deveRetornarBadRequestParaTentativaDeRegistroDeEntrega() throws IOException {
            StubConfig.registraEntregaMockResponseBadRequest(1L, 1L);
            EntregaDTO entregaDTO = new EntregaDTO(null, "PENDENTE", 1L, 1L);
            assertThatThrownBy(() -> entregaGateway.registraEntrega(entregaDTO))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("bad request");
        }

    }

}
