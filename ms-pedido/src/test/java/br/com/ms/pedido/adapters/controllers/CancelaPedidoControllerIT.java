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
public class CancelaPedidoControllerIT {

    @Autowired
    private CancelaPedidoController controller;

    @Autowired
    private PedidoRepository pedidoRepository;

    private PedidoModel pedidoModelSetup;

    @BeforeEach
    public void setup() {
        List<Map<String, Object>> produtos = List.of(Map.of("id", 1L, "quantidade", 10),
                Map.of("id", 2L, "quantidade", 20));
        PedidoModel pedidoModel = new PedidoModel(null, 1L, StatusPedido.EM_ABERTO, null, produtos, null, null, null, null);
        pedidoModelSetup = pedidoRepository.save(pedidoModel);
    }

    @Nested
    public class CancelandoPedido {

        @Test
        @DisplayName("deve lancar excecao para pedido nao encontrado")
        public void deveLancarExcecaoParaPedidoNaoEncontrado() {
            assertThatThrownBy(() -> controller.run(0L))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("pedido não encontrado");
        }

        @Test
        @DisplayName("deve lancar excecao para pedido ja cancelado")
        public void deveLancarExcecaoParaPedidoJaCancelado() {
            Optional<PedidoModel> optional = pedidoRepository.findById(pedidoModelSetup.getId());
            if (optional.isEmpty())
                throw new NoSuchElementException("pedido nao encontrado");
            PedidoModel pedidoModel = optional.get();
            pedidoModel.setStatus(StatusPedido.CANCELADO);
            pedidoRepository.save(pedidoModel);
            assertThatThrownBy(() -> controller.run(pedidoModelSetup.getId()))
                    .isInstanceOf(PedidoException.class)
                    .hasMessage("pedido já está cancelado");
        }

        @Test
        @DisplayName("deve lancar excecao para pedido já concluido")
        public void deveLancarExcecaoParaPedidoJaConcluido() {
            Optional<PedidoModel> optional = pedidoRepository.findById(pedidoModelSetup.getId());
            if (optional.isEmpty())
                throw new NoSuchElementException("pedido nao encontrado");
            PedidoModel pedidoModel = optional.get();
            pedidoModel.setStatus(StatusPedido.CONCLUIDO);
            pedidoRepository.save(pedidoModel);
            assertThatThrownBy(() -> controller.run(pedidoModelSetup.getId()))
                    .isInstanceOf(PedidoException.class)
                    .hasMessage("pedido já está concluído");
        }

        @Test
        @DisplayName("deve lancar excecao para pedido com produto inexistente")
        public void deveLancarExcecaoParaPedidoComProdutoInexistente() throws IOException {
            StubConfig.incrementaEstoqueMockResponseNotFound(1L, 10);
            Optional<PedidoModel> optional = pedidoRepository.findById(pedidoModelSetup.getId());
            if (optional.isEmpty())
                throw new NoSuchElementException("pedido nao encontrado");
            PedidoModel pedidoModel = optional.get();
            pedidoModel.setStatus(StatusPedido.CONFIRMADO);
            pedidoRepository.save(pedidoModel);
            assertThatThrownBy(() -> controller.run(pedidoModelSetup.getId()))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("not found");
        }

        @Test
        @DisplayName("deve cancelar pedido confirmado corretamente")
        public void deveCancelarPedidoConfirmadoCorretamente() throws IOException {
            StubConfig.incrementaEstoqueMockResponseNotFound(1L, 10);
            Optional<PedidoModel> optional = pedidoRepository.findById(pedidoModelSetup.getId());
            if (optional.isEmpty())
                throw new NoSuchElementException("pedido nao encontrado");
            PedidoModel pedidoModel = optional.get();
            pedidoModel.setStatus(StatusPedido.CONFIRMADO);
            pedidoRepository.save(pedidoModel);
            StubConfig.incrementaEstoqueMockResponseOK(1L, 10);
            StubConfig.incrementaEstoqueMockResponseOK(2L, 20);
            controller.run(pedidoModelSetup.getId());
        }

        @Test
        @DisplayName("deve cancelar pedido em aberto corretamente")
        public void deveCancelarPedidoEmAbertoCorretamente() {
            controller.run(pedidoModelSetup.getId());
        }

    }

}


