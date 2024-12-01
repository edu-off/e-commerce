package br.com.ms.logistica.adapters.controllers;

import br.com.ms.logistica.application.dto.EntregadorDTO;
import br.com.ms.logistica.application.exceptions.EntregadorException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
@AutoConfigureTestDatabase
public class AtualizaEntregadorControllerIT {

    @Autowired
    private RegistraEntregadorController registraEntregadorController;

    @Autowired
    private AtualizaEntregadorController atualizaEntregadorController;

    private EntregadorDTO entregadorDTOSetup;

    @BeforeEach
    public void setup() {
        EntregadorDTO entregadorDTO = new EntregadorDTO(null, "DISPONIVEL", "nome", "teste@teste.com.br");
        entregadorDTOSetup = registraEntregadorController.run(entregadorDTO);
    }

    @Nested
    public class AtualizandoEntregador {

        @Test
        @DisplayName("deve lancar excecao para entregador invalido")
        public void deveLancarExcecaoParaEntregadorInvalido() {
            EntregadorDTO entregadorDTO = new EntregadorDTO(null, "DISPONIVEL", "", "teste@teste.com.br");
            assertThatThrownBy(() -> atualizaEntregadorController.run(1L, entregadorDTO))
                    .isInstanceOf(EntregadorException.class)
                    .hasMessage("erro ao validar entregador: nome inválido");
        }

        @Test
        @DisplayName("deve lancar excecao para entregador inexistente")
        public void deveLancarExcecaoParaEntregadorInexistente() {
            EntregadorDTO entregadorDTO = new EntregadorDTO(null, "DISPONIVEL", "nome", "teste@teste.com.br");
            assertThatThrownBy(() -> atualizaEntregadorController.run(0L, entregadorDTO))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("entregador não encontrado");
        }


        @Test
        @DisplayName("deve atualizar entregador corretamente")
        public void deveAtualizarEntregadorCorretamente() {
            EntregadorDTO entregadorDTO = new EntregadorDTO(null, "DISPONIVEL", "nome", "teste@teste.com.br");
            EntregadorDTO entregadorDTOCreated = atualizaEntregadorController.run(entregadorDTOSetup.getId(), entregadorDTO);
            assertThat(entregadorDTOCreated).isInstanceOf(EntregadorDTO.class).isNotNull();
        }

    }

}
