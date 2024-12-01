package br.com.ms.logistica.adapters.controllers;

import br.com.ms.logistica.config.StubConfig;
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

@Transactional
@ActiveProfiles("test")
@AutoConfigureTestDatabase
@AutoConfigureWireMock(port = 0)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IniciaEntregaControllerIT {

    @Autowired
    private IniciaEntregaController iniciaEntregaController;

    @Autowired
    private EntregaRepository entregaRepository;

    @Autowired
    private EntregadorRepository entregadorRepository;

    @BeforeEach
    public void setup() {
        entregaRepository.deleteAll();
        entregadorRepository.deleteAll();
    }

    @Nested
    public class IniciandoEntregas {

        @Test
        @DisplayName("nao deve haver entregas pendentes")
        public void naoDeveHaverEntregasPendentes() {
            iniciaEntregaController.run();
        }

        @Test
        @DisplayName("nao deve haver entregadores disponiveis")
        public void naoDeveHaverEntregadoresDisponiveis() {
            setup(true, false);
            iniciaEntregaController.run();
        }

        @Test
        @DisplayName("deve iniciar entregas pendentes")
        public void deveIniciarEntregasPendentes() throws IOException {
            StubConfig.recuperaClienteMockResponseOK(1L);
            setup(true, true);
            iniciaEntregaController.run();
        }

    }

    public void setup(boolean withEntrega, boolean withEntregador) {
        if (withEntrega)
            entregaRepository.save(new EntregaModel(null, StatusEntrega.PENDENTE, 1L, 1L, null, null, null, null, null, null, null, null, null));
        if (withEntregador)
            entregadorRepository.save(new EntregadorModel(null, "nome", StatusEntregador.DISPONIVEL, "teste@teste.com.br", null));
    }

}
