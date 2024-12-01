package br.com.ms.logistica.adapters.controllers;

import br.com.ms.logistica.adapters.presenters.EntregadorPresenter;
import br.com.ms.logistica.application.dto.EntregadorDTO;
import br.com.ms.logistica.application.exceptions.EntregadorException;
import br.com.ms.logistica.application.usecases.AtualizaEntregador;
import br.com.ms.logistica.application.usecases.ValidaEntregador;
import br.com.ms.logistica.domain.entities.EntregadorEntity;
import br.com.ms.logistica.domain.enums.StatusEntregador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

public class AtualizaEntregadorControllerTest {

    @Mock
    private ValidaEntregador validaEntregador;

    @Mock
    private AtualizaEntregador atualizaEntregador;

    @Mock
    private EntregadorPresenter presenter;

    @InjectMocks
    private AtualizaEntregadorController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class AtualizandoEntregador {

        @Test
        @DisplayName("deve lancar excecao para entregador invalido")
        public void deveLancarExcecaoParaEntregadorInvalido() {
            EntregadorDTO entregadorDTO = new EntregadorDTO(null, "DISPONIVEL", "nome", "teste@teste.com.br");
            when(validaEntregador.run(entregadorDTO)).thenThrow(new EntregadorException("erro ao validar entregador: nome inválido"));
            assertThatThrownBy(() -> controller.run(1L, entregadorDTO))
                    .isInstanceOf(EntregadorException.class)
                    .hasMessage("erro ao validar entregador: nome inválido");
        }

        @Test
        @DisplayName("deve lancar excecao para entregador inexistente")
        public void deveLancarExcecaoParaEntregadorInexistente() {
            EntregadorDTO entregadorDTO = new EntregadorDTO(null, "DISPONIVEL", "nome", "teste@teste.com.br");
            when(validaEntregador.run(entregadorDTO)).thenThrow(new NoSuchElementException("entregador não encontrado"));
            assertThatThrownBy(() -> controller.run(1L, entregadorDTO))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("entregador não encontrado");
        }


        @Test
        @DisplayName("deve atualizar entregador corretamente")
        public void deveAtualizarEntregadorCorretamente() {
            EntregadorDTO entregadorDTO = new EntregadorDTO(null, "DISPONIVEL", "nome", "teste@teste.com.br");
            EntregadorEntity entregadorEntity = new EntregadorEntity(StatusEntregador.DISPONIVEL, "nome", "teste@teste.com.br");
            when(validaEntregador.run(entregadorDTO)).thenReturn(entregadorEntity);
            when(atualizaEntregador.run(1L, entregadorEntity)).thenReturn(entregadorEntity);
            when(presenter.transform(entregadorEntity)).thenReturn(entregadorDTO);
            EntregadorDTO entregadorDTOCreated = controller.run(1L, entregadorDTO);
            assertThat(entregadorDTOCreated).isInstanceOf(EntregadorDTO.class).isNotNull();
        }

    }

}
