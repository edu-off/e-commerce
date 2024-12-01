package br.com.ms.logistica.adapters.controllers;

import br.com.ms.logistica.application.dto.EntregaDTO;
import br.com.ms.logistica.application.exceptions.EntregaException;
import br.com.ms.logistica.config.StubConfig;
import br.com.ms.logistica.domain.entities.EntregaEntity;
import br.com.ms.logistica.domain.enums.StatusEntrega;
import br.com.ms.logistica.domain.enums.StatusEntregador;
import br.com.ms.logistica.infrastructure.models.EntregaModel;
import br.com.ms.logistica.infrastructure.models.EntregadorModel;
import br.com.ms.logistica.infrastructure.repositories.EntregaRepository;
import br.com.ms.logistica.infrastructure.repositories.EntregadorRepository;
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
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@ActiveProfiles("test")
@AutoConfigureTestDatabase
@AutoConfigureWireMock(port = 0)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CancelaEntregaControllerIT {

    @Autowired
    private CancelaEntregaController cancelaEntregaController;

    @Autowired
    private EntregaRepository entregaRepository;

    @Autowired
    private EntregadorRepository entregadorRepository;

    private EntregaModel entregaModelSetup;

    @BeforeEach
    public void setUp() {
        EntregadorModel entregadorModel = new EntregadorModel(null, "nome", StatusEntregador.DISPONIVEL, "teste@teste.com.br", null);
        EntregadorModel entregadorModelCreated = entregadorRepository.save(entregadorModel);
        EntregaModel entregaModel = new EntregaModel(null, StatusEntrega.PENDENTE, 1L, 1L, null, null, null, null, null, null, null, null, entregadorModelCreated);
        entregaModelSetup = entregaRepository.save(entregaModel);
    }

    @Nested
    public class CancelandoEntrega {

        @Test
        @DisplayName("deve lancar excecao para entrega nao encontrada")
        public void deveLancarExcecaoParaEntregaNaoEncontradaNaBusca() {
            assertThatThrownBy(() -> cancelaEntregaController.run(0L))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("entrega não encontrada");
        }

        @Test
        @DisplayName("deve lancar excecao para entrega já cancelada")
        public void deveLancarExcecaoParaEntregaJaCancelada() {
            Optional<EntregaModel> optional = entregaRepository.findById(entregaModelSetup.getId());
            if (optional.isEmpty())
                throw new NoSuchElementException("entrega não encontrada");
            EntregaModel entregaModel = optional.get();
            entregaModel.setStatus(StatusEntrega.CANCELADA);
            entregaRepository.save(entregaModel);
            assertThatThrownBy(() -> cancelaEntregaController.run(entregaModelSetup.getId()))
                    .isInstanceOf(EntregaException.class)
                    .hasMessage("entrega já está cancelada");
        }

        @Test
        @DisplayName("deve lancar excecao para entrega concluida")
        public void deveLancarExcecaoParaEntregaConcluida() {
            Optional<EntregaModel> optional = entregaRepository.findById(entregaModelSetup.getId());
            if (optional.isEmpty())
                throw new NoSuchElementException("entrega não encontrada");
            EntregaModel entregaModel = optional.get();
            entregaModel.setStatus(StatusEntrega.CONCLUIDA);
            entregaRepository.save(entregaModel);
            assertThatThrownBy(() -> cancelaEntregaController.run(entregaModelSetup.getId()))
                    .isInstanceOf(EntregaException.class)
                    .hasMessage("entrega já está concluída");
        }

        @Test
        @DisplayName("deve lancar excecao para entrega em transito")
        public void deveLancarExcecaoParaEntregaEmTransito() {
            Optional<EntregaModel> optional = entregaRepository.findById(entregaModelSetup.getId());
            if (optional.isEmpty())
                throw new NoSuchElementException("entrega não encontrada");
            EntregaModel entregaModel = optional.get();
            entregaModel.setStatus(StatusEntrega.EM_TRANSITO);
            entregaRepository.save(entregaModel);
            assertThatThrownBy(() -> cancelaEntregaController.run(entregaModelSetup.getId()))
                    .isInstanceOf(EntregaException.class)
                    .hasMessage("entrega em trânsito não pode ser cancelada");
        }

        @Test
        @DisplayName("deve lancar excecao para pedido nao encontrado")
        public void deveLancarExcecaoParaPedidoNaoEncontrado() throws IOException {
            StubConfig.cancelaPedidoMockResponseNotFound(entregaModelSetup.getPedidoId());
            assertThatThrownBy(() -> cancelaEntregaController.run(entregaModelSetup.getId()))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("not found");
        }

        @Test
        @DisplayName("deve lancar excecao para entregador nao encontrado")
        public void deveLancarExcecaoParaEntregadorNaoEncontrado() throws IOException {
            StubConfig.cancelaPedidoMockResponseOK(entregaModelSetup.getPedidoId());
            entregaModelSetup.getEntregador().setId(0L);
            assertThatThrownBy(() -> cancelaEntregaController.run(entregaModelSetup.getId()))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("entregador não encontrado");
        }

        @Test
        @DisplayName("deve cancelar entrega corretamente")
        public void deveCancelarEntregaCorretamente() throws IOException {
            StubConfig.cancelaPedidoMockResponseOK(entregaModelSetup.getPedidoId());
            cancelaEntregaController.run(entregaModelSetup.getId());
        }

    }

}
