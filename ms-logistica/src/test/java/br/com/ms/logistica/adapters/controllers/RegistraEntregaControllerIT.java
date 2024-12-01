package br.com.ms.logistica.adapters.controllers;

import br.com.ms.logistica.application.dto.EntregaDTO;
import br.com.ms.logistica.application.exceptions.EntregaException;
import br.com.ms.logistica.config.StubConfig;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@ActiveProfiles("test")
@AutoConfigureTestDatabase
@AutoConfigureWireMock(port = 0)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegistraEntregaControllerIT {

    @Autowired
    private RegistraEntregaController controller;

    @Nested
    public class RegistrandoEntrega {

        @Test
        @DisplayName("deve lancar excecao para entrega invalida")
        public void deveLancarExcecaoParaEntregaInvalida() {
            EntregaDTO entregaDTO = new EntregaDTO(null, "", 1L, 1L);
            assertThatThrownBy(() -> controller.run(entregaDTO))
                    .isInstanceOf(EntregaException.class)
                    .hasMessage("erro ao validar entrega: status inválido");
        }

        @Test
        @DisplayName("deve lancar excecao para cliente nao encontrado")
        public void deveLancarExcecaoParaClienteNaoEncontrado() throws IOException {
            StubConfig.recuperaClienteMockResponseNotFound(1L);
            EntregaDTO entregaDTO = new EntregaDTO(null, "PENDENTE", 1L, 1L);
            assertThatThrownBy(() -> controller.run(entregaDTO))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("not found");
        }

        @Test
        @DisplayName("deve lancar excecao para pedido nao encontrado")
        public void deveLancarExcecaoParaPedidoNaoEncontrado() throws IOException {
            StubConfig.recuperaClienteMockResponseOK(1L);
            StubConfig.recuperaPedidoMockResponseNotFound(1L);
            EntregaDTO entregaDTO = new EntregaDTO(null, "PENDENTE", 1L, 1L);
            assertThatThrownBy(() -> controller.run(entregaDTO))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("not found");
        }

        @Test
        @DisplayName("deve registrar entrega corretamente")
        public void deveRegistrarEntregaCorretamente() throws IOException {
            StubConfig.recuperaClienteMockResponseOK(1L);
            StubConfig.recuperaPedidoMockResponseOK(1L, 1L);
            EntregaDTO entregaDTO = new EntregaDTO(null, "PENDENTE", 1L, 1L);
            EntregaDTO entregaDTOCreated = controller.run(entregaDTO);
            assertThat(entregaDTOCreated).isInstanceOf(EntregaDTO.class).isNotNull();
        }

    }

}
