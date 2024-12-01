package br.com.ms.logistica.adapters.controllers;

import br.com.ms.logistica.application.dto.EntregaDTO;
import br.com.ms.logistica.application.exceptions.EntregaException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
@AutoConfigureTestDatabase
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
        @DisplayName("deve registrar entrega corretamente")
        public void deveRegistrarEntregaCorretamente() {
            EntregaDTO entregaDTO = new EntregaDTO(null, "PENDENTE", 1L, 1L);
            EntregaDTO entregaDTOCreated = controller.run(entregaDTO);
            assertThat(entregaDTOCreated).isInstanceOf(EntregaDTO.class).isNotNull();
        }

    }

}
