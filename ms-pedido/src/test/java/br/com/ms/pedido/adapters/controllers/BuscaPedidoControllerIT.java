package br.com.ms.pedido.adapters.controllers;

import br.com.ms.pedido.domain.enums.StatusPedido;
import br.com.ms.pedido.infrastructure.models.PedidoModel;
import br.com.ms.pedido.infrastructure.repositories.PedidoRepository;
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

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
@AutoConfigureTestDatabase
public class BuscaPedidoControllerIT {

    @Autowired
    private BuscaPedidoController controller;

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
    public class BuscandoPedido {

        @Test
        @DisplayName("deve lancar excecao para pedido nao encontrado")
        public void deveLancarExcecaoParaPedidoNaoEncontrado() {
            assertThatThrownBy(() -> controller.run(0L))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("pedido não encontrado");
        }

        @Test
        @DisplayName("deve buscar pedido corretamente")
        public void deveBuscarPedidoCorretamente() {
            controller.run(pedidoModelSetup.getId());
        }

    }

}
