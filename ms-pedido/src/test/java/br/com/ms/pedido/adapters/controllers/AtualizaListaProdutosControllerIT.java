package br.com.ms.pedido.adapters.controllers;

import br.com.ms.pedido.application.dto.PedidoDTO;
import br.com.ms.pedido.application.dto.ProdutoDTO;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@ActiveProfiles("test")
@AutoConfigureTestDatabase
@AutoConfigureWireMock(port = 0)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AtualizaListaProdutosControllerIT {

    @Autowired
    private AtualizaListaProdutosController controller;

    @Autowired
    private PedidoRepository pedidoRepository;

    private PedidoModel pedidoModelSetup;

    @BeforeEach
    public void setup() {
        List<Map<String, Object>> produtos = List.of(Map.of("id", 1L, "quantidade", 1),
                Map.of("id", 2L, "quantidade", 2));
        PedidoModel pedidoModel = new PedidoModel(null, 1L, StatusPedido.EM_ABERTO, 0.0, produtos, null, null, null, null);
        pedidoModelSetup = pedidoRepository.save(pedidoModel);
    }

    @Nested
    public class AtualizandoListaProdutos {

        @Test
        @DisplayName("deve lancar excecao para pedido nao encontrado")
        public void deveLancarExcecaoParaPedidoNaoEncontrado() {
            List<ProdutoDTO> produtos = List.of(new ProdutoDTO(1L, 20, 0.0), new ProdutoDTO(2L, 20, 0.0));
            assertThatThrownBy(() -> controller.run(0L, produtos))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("pedido não encontrado");
        }

        @Test
        @DisplayName("deve lancar excecao para pedido que não está em aberto")
        public void deveLancarExcecaoParaPedidoQueNaoEstaEmAberto() {
            Optional<PedidoModel> optional = pedidoRepository.findById(pedidoModelSetup.getId());
            if (optional.isEmpty())
                throw new NoSuchElementException("pedido nao encontrado");
            PedidoModel pedidoModel = optional.get();
            pedidoModel.setStatus(StatusPedido.CONFIRMADO);
            pedidoRepository.save(pedidoModel);
            List<ProdutoDTO> produtos = List.of(new ProdutoDTO(1L, 20, 0.0), new ProdutoDTO(2L, 20, 0.0));
            assertThatThrownBy(() -> controller.run(pedidoModelSetup.getId(), produtos))
                    .isInstanceOf(PedidoException.class)
                    .hasMessage("pedidos que não estão em aberto não podem sofrer atualizações");
        }

        @Test
        @DisplayName("deve lancar excecao para produto não encontrado")
        public void deveLancarExcecaoParaProdutoNaoEncontrado() throws IOException {
            StubConfig.recuperaProdutoMockResponseNotFound(1L);
            List<ProdutoDTO> produtos = List.of(new ProdutoDTO(1L, 20, 0.0),
                    new ProdutoDTO(2L, 20, 0.0),
                    new ProdutoDTO(3L, 20, 0.0),
                    new ProdutoDTO(4L, 20, 0.0));
            assertThatThrownBy(() -> controller.run(pedidoModelSetup.getId(), produtos))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("not found");
        }

        @Test
        @DisplayName("deve atualizar lista de produtos corretamente")
        public void deveAtualizarListaDeProdutosCorretamente() throws IOException {
            StubConfig.recuperaProdutoMockResponseOK(1L, 20, 0.0);
            StubConfig.recuperaProdutoMockResponseOK(2L, 20, 0.0);
            StubConfig.recuperaProdutoMockResponseOK(3L, 20, 0.0);
            StubConfig.recuperaProdutoMockResponseOK(4L, 20, 0.0);
            List<ProdutoDTO> produtos = List.of(new ProdutoDTO(1L, 20, 0.0),
                    new ProdutoDTO(2L, 20, 0.0),
                    new ProdutoDTO(3L, 20, 0.0),
                    new ProdutoDTO(4L, 20, 0.0));
            PedidoDTO pedidoDTO = controller.run(pedidoModelSetup.getId(), produtos);
            assertThat(pedidoDTO).isInstanceOf(PedidoDTO.class).isNotNull();
            assertThat(pedidoDTO.getProdutos()).hasSize(4);
            assertThat(pedidoDTO.getProdutos().get(0).getId()).isEqualTo(1L);
            assertThat(pedidoDTO.getProdutos().get(0).getQuantidade()).isEqualTo(20);
        }

    }

}


