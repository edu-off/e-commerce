package br.com.ms.pedido.adapters.controllers;

import br.com.ms.pedido.application.exceptions.PedidoException;
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

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ConcluiPedidoControllerIT {

    @Autowired
    private ConcluiPedidoController controller;

    @Autowired
    private PedidoRepository pedidoRepository;

    private PedidoModel pedidoModelSetup;

    @BeforeEach
    public void setup() {
        List<Map<String, Object>> produtos = List.of(Map.of("id", 1L, "quantidade", 1),
                Map.of("id", 2L, "quantidade", 2));
        PedidoModel pedidoModel = new PedidoModel(null, 1L, StatusPedido.EM_ABERTO, null, produtos, null, null, null, null);
        pedidoModelSetup = pedidoRepository.save(pedidoModel);
    }

    @Nested
    public class ConcluindoPedido {

        @Test
        @DisplayName("deve lancar excecao para pedido nao encontrado")
        public void deveLancarExcecaoParaPedidoNaoEncontrado() {
            assertThatThrownBy(() -> controller.run(0L))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("pedido não encontrado");
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
                    .hasMessage("pedido já está concluído");
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
        @DisplayName("deve lancar excecao para pedido em aberto")
        public void deveLancarExcecaoParaPedidoEmAberto() {
            Optional<PedidoModel> optional = pedidoRepository.findById(pedidoModelSetup.getId());
            if (optional.isEmpty())
                throw new NoSuchElementException("pedido nao encontrado");
            PedidoModel pedidoModel = optional.get();
            pedidoModel.setStatus(StatusPedido.EM_ABERTO);
            pedidoRepository.save(pedidoModel);
            assertThatThrownBy(() -> controller.run(pedidoModelSetup.getId()))
                    .isInstanceOf(PedidoException.class)
                    .hasMessage("pedido ainda está em aberto");
        }

        @Test
        @DisplayName("deve concluir pedido corretamente")
        public void deveConcluirPedidoCorretamente() {
            Optional<PedidoModel> optional = pedidoRepository.findById(pedidoModelSetup.getId());
            if (optional.isEmpty())
                throw new NoSuchElementException("pedido não encontrado");
            PedidoModel pedidoModel = optional.get();
            pedidoModel.setStatus(StatusPedido.CONFIRMADO);
            pedidoRepository.save(pedidoModel);
            controller.run(pedidoModelSetup.getId());
        }

    }

}


