package br.com.ms.pedido.adapters.controllers;

import br.com.ms.pedido.application.dto.PedidoDTO;
import br.com.ms.pedido.application.dto.ProdutoDTO;
import br.com.ms.pedido.application.exceptions.PedidoException;
import br.com.ms.pedido.config.StubConfig;
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
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@ActiveProfiles("test")
@AutoConfigureTestDatabase
@AutoConfigureWireMock(port = 0)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegistraPedidoControllerIT {

    @Autowired
    private RegistraPedidoController controller;

    @Nested
    public class RegistrandoPedido {

        @Test
        @DisplayName("deve lancar excecao para pedido invalido")
        public void deveLancarExcecaoParaPedidoInvalido() {
            List<ProdutoDTO> produtos = List.of(new ProdutoDTO(1L, 1, 0.0), new ProdutoDTO(2L, 2, 0.0));
            PedidoDTO pedidoDTO = new PedidoDTO(null, 0L, "EM_ABERTO", produtos);
            assertThatThrownBy(() -> controller.run(pedidoDTO))
                    .isInstanceOf(PedidoException.class)
                    .hasMessage("erro ao validar pedido: identificador do cliente inválido");
        }

        @Test
        @DisplayName("deve lancar excecao para cliente inválido")
        public void deveLancarExcecaoParaClienteInvalido() throws IOException {
            StubConfig.recuperaClienteMockResponseNotFound(1L);
            List<ProdutoDTO> produtos = List.of(new ProdutoDTO(1L, 1, 0.0), new ProdutoDTO(2L, 2, 0.0));
            PedidoDTO pedidoDTO = new PedidoDTO(null, 1L, "EM_ABERTO", produtos);
            assertThatThrownBy(() -> controller.run(pedidoDTO))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("not found");
        }

        @Test
        @DisplayName("deve lancar excecao para produto não encontrado")
        public void deveLancarExcecaoParaProdutoNaoEncontrado() throws IOException {
            StubConfig.recuperaClienteMockResponseOK(1L);
            StubConfig.recuperaProdutoMockResponseNotFound(1L);
            List<ProdutoDTO> produtos = List.of(new ProdutoDTO(1L, 1, 0.0), new ProdutoDTO(2L, 2, 0.0));
            PedidoDTO pedidoDTO = new PedidoDTO(null, 1L, "EM_ABERTO", produtos);
            assertThatThrownBy(() -> controller.run(pedidoDTO))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("not found");
        }

        @Test
        @DisplayName("deve registrar pedido corretamente")
        public void deveRegistrarPedidoCorretamente() throws IOException {
            StubConfig.recuperaClienteMockResponseOK(1L);
            StubConfig.recuperaProdutoMockResponseOK(1L, 1, 0.0);
            StubConfig.recuperaProdutoMockResponseOK(2L, 2, 0.0);
            List<ProdutoDTO> produtos = List.of(new ProdutoDTO(1L, 1, 0.0), new ProdutoDTO(2L, 2, 0.0));
            PedidoDTO pedidoDTO = new PedidoDTO(null, 1L, "EM_ABERTO", produtos);
            PedidoDTO pedidoDTOCreated = controller.run(pedidoDTO);
            assertThat(pedidoDTOCreated).isInstanceOf(PedidoDTO.class).isNotNull();
        }

    }

}
