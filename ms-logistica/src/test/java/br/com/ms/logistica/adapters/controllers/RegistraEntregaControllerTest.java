package br.com.ms.logistica.adapters.controllers;

import br.com.ms.logistica.adapters.presenters.EntregaPresenter;
import br.com.ms.logistica.application.dto.EntregaDTO;
import br.com.ms.logistica.application.exceptions.EntregaException;
import br.com.ms.logistica.application.usecases.SalvaEntrega;
import br.com.ms.logistica.application.usecases.ValidaEntrega;
import br.com.ms.logistica.domain.entities.EntregaEntity;
import br.com.ms.logistica.domain.enums.StatusEntrega;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

public class RegistraEntregaControllerTest {

    @Mock
    private ValidaEntrega validaEntrega;

    @Mock
    private SalvaEntrega salvaEntrega;

    @Mock
    private EntregaPresenter presenter;

    @InjectMocks
    private RegistraEntregaController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class RegistrandoEntrega {

        @Test
        @DisplayName("deve lancar excecao para Entrega invalido")
        public void deveLancarExcecaoParaEntregaInvalido() {
            EntregaDTO EntregaDTO = new EntregaDTO(null, "PENDENTE", 1L, 1L);
            when(validaEntrega.run(EntregaDTO)).thenThrow(new EntregaException("erro ao validar Entrega: nome inválido"));
            assertThatThrownBy(() -> controller.run(EntregaDTO))
                    .isInstanceOf(EntregaException.class)
                    .hasMessage("erro ao validar Entrega: nome inválido");
        }

        @Test
        @DisplayName("deve registrar Entrega corretamente")
        public void deveRegistrarEntregaCorretamente() {
            EntregaDTO EntregaDTO = new EntregaDTO(null, "PENDENTE", 1L, 1L);
            EntregaEntity EntregaEntity = new EntregaEntity(StatusEntrega.PENDENTE, 1L, 1L);
            when(validaEntrega.run(EntregaDTO)).thenReturn(EntregaEntity);
            when(salvaEntrega.run(EntregaEntity)).thenReturn(EntregaEntity);
            when(presenter.transform(EntregaEntity)).thenReturn(EntregaDTO);
            EntregaDTO EntregaDTOCreated = controller.run(EntregaDTO);
            assertThat(EntregaDTOCreated).isInstanceOf(EntregaDTO.class).isNotNull();
        }

    }
}
