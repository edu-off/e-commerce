package br.com.ms.logistica.adapters.controllers;

import br.com.ms.logistica.application.dto.EntregadorDTO;
import br.com.ms.logistica.application.exceptions.EntregadorException;
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
public class RegistraEntregadorControllerIT {

    @Autowired
    private RegistraEntregadorController controller;

    @Nested
    public class RegistrandoEntregador {

        @Test
        @DisplayName("deve lancar excecao para entregador invalido")
        public void deveLancarExcecaoParaEntregadorInvalido() {
            EntregadorDTO entregadorDTO = new EntregadorDTO(null, "DISPONIVEL", "", "teste@teste.com.br");
            assertThatThrownBy(() -> controller.run(entregadorDTO))
                    .isInstanceOf(EntregadorException.class)
                    .hasMessage("erro ao validar entregador: nome inválido");
        }

        @Test
        @DisplayName("deve registrar entregador corretamente")
        public void deveRegistrarEntregadorCorretamente() {
            EntregadorDTO entregadorDTO = new EntregadorDTO(null, "DISPONIVEL", "nome", "teste@teste.com.br");
            EntregadorDTO entregadorDTOCreated = controller.run(entregadorDTO);
            assertThat(entregadorDTOCreated).isInstanceOf(EntregadorDTO.class).isNotNull();
        }

    }

}
