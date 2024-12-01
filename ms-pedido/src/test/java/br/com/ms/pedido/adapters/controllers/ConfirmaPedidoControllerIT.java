package br.com.ms.pedido.adapters.controllers;

import br.com.ms.pedido.application.exceptions.PedidoException;
import br.com.ms.pedido.config.StubConfig;
import br.com.ms.pedido.domain.enums.StatusPedido;
import br.com.ms.pedido.infrastructure.models.PedidoModel;
import br.com.ms.pedido.infrastructure.repositories.PedidoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@ActiveProfiles("test")
@AutoConfigureTestDatabase
@AutoConfigureWireMock(port = 0)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ConfirmaPedidoControllerIT {

    @Autowired
    private ConfirmaPedidoController controller;

    @Autowired
    private PedidoRepository pedidoRepository;

    private PedidoModel pedidoModelSetup;

    @BeforeEach
    public void setup() {
        List<Map<String, Object>> produtos = List.of(Map.of("id", 1L, "quantidade", 2),
                Map.of("id", 2L, "quantidade", 2));
        PedidoModel pedidoModel = new PedidoModel(null, 1L, StatusPedido.EM_ABERTO, null, produtos, null, null, null, null);
        pedidoModelSetup = pedidoRepository.save(pedidoModel);
    }

    @Nested
    public class ConfirmandoPedido {

        @Test
        @DisplayName("deve lancar excecao para pedido nao encontrado")
        public void deveLancarExcecaoParaPedidoNaoEncontrado() {
            assertThatThrownBy(() -> controller.run(0L))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("pedido não encontrado");
        }

        @Test
        @DisplayName("deve lancar excecao para pedido confirmado")
        public void deveLancarExcecaoParaPedidoConfirmado() {
            Optional<PedidoModel> optional = pedidoRepository.findById(pedidoModelSetup.getId());
            if (optional.isEmpty())
                throw new NoSuchElementException("pedido nao encontrado");
            PedidoModel pedidoModel = optional.get();
            pedidoModel.setStatus(StatusPedido.CONFIRMADO);
            pedidoRepository.save(pedidoModel);
            assertThatThrownBy(() -> controller.run(pedidoModelSetup.getId()))
                    .isInstanceOf(PedidoException.class)
                    .hasMessage("pedido já está confirmado");
        }

        @Test
        @DisplayName("deve lancar excecao para pedido cancelado")
        public void deveLancarExcecaoParaPedidoCancelado() {
            Optional<PedidoModel> optional = pedidoRepository.findById(pedidoModelSetup.getId());
            if (optional.isEmpty())
                throw new NoSuchElementException("pedido nao encontrado");
            PedidoModel pedidoModel = optional.get();
            pedidoModel.setStatus(StatusPedido.CANCELADO);
            pedidoRepository.save(pedidoModel);
            assertThatThrownBy(() -> controller.run(pedidoModelSetup.getId()))
                    .isInstanceOf(PedidoException.class)
                    .hasMessage("pedido cancelado");
        }

        @Test
        @DisplayName("deve lancar excecao para pedido concluido")
        public void deveLancarExcecaoParaPedidoConcluido() {
            Optional<PedidoModel> optional = pedidoRepository.findById(pedidoModelSetup.getId());
            if (optional.isEmpty())
                throw new NoSuchElementException("pedido nao encontrado");
            PedidoModel pedidoModel = optional.get();
            pedidoModel.setStatus(StatusPedido.CONCLUIDO);
            pedidoRepository.save(pedidoModel);
            assertThatThrownBy(() -> controller.run(pedidoModelSetup.getId()))
                    .isInstanceOf(PedidoException.class)
                    .hasMessage("pedido concluído");
        }

        @Test
        @DisplayName("deve confirmar pedido corretamente")
        public void deveConfirmarPedidoCorretamente() throws IOException {
            StubConfig.recuperaProdutoMockResponseOK(1L, 10000, 0.0);
            StubConfig.recuperaProdutoMockResponseOK(2L, 10000, 0.0);
            StubConfig.registraEntregaMockResponseOK(1L, pedidoModelSetup.getId());
            StubConfig.decrementaEstoqueMockResponseOK(1L, 2);
            StubConfig.decrementaEstoqueMockResponseOK(2L, 2);
            controller.run(pedidoModelSetup.getId());
        }

    }

}


