package br.com.ms.logistica.adapters.controllers;

import br.com.ms.logistica.adapters.presenters.EntregaPresenter;
import br.com.ms.logistica.application.dto.EntregaDTO;
import br.com.ms.logistica.application.exceptions.EntregaException;
import br.com.ms.logistica.application.usecases.BuscaCliente;
import br.com.ms.logistica.application.usecases.BuscaPedido;
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

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class RegistraEntregaControllerTest {

    @Mock
    private ValidaEntrega validaEntrega;

    @Mock
    private BuscaCliente buscaCliente;

    @Mock
    private BuscaPedido buscaPedido;

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
        @DisplayName("deve lancar excecao para entrega invalido")
        public void deveLancarExcecaoParaEntregaInvalido() {
            EntregaDTO EntregaDTO = new EntregaDTO(null, "PENDENTE", 1L, 1L);
            when(validaEntrega.run(EntregaDTO)).thenThrow(new EntregaException("erro ao validar Entrega: nome inválido"));
            assertThatThrownBy(() -> controller.run(EntregaDTO))
                    .isInstanceOf(EntregaException.class)
                    .hasMessage("erro ao validar Entrega: nome inválido");
        }

        @Test
        @DisplayName("deve lancar excecao para cliente nao encontrado")
        public void deveLancarExcecaoParaClienteNaoEncontrado() {
            EntregaDTO EntregaDTO = new EntregaDTO(null, "PENDENTE", 1L, 1L);
            EntregaEntity EntregaEntity = new EntregaEntity(StatusEntrega.PENDENTE, 1L, 1L);
            when(validaEntrega.run(EntregaDTO)).thenReturn(EntregaEntity);
            doThrow(new NoSuchElementException("cliente não encontrado")).when(buscaCliente).run(1L);
            assertThatThrownBy(() -> controller.run(EntregaDTO))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("cliente não encontrado");
        }

        @Test
        @DisplayName("deve lancar excecao para pedido nao encontrado")
        public void deveLancarExcecaoParaPedidoNaoEncontrado() {
            EntregaDTO EntregaDTO = new EntregaDTO(null, "PENDENTE", 1L, 1L);
            EntregaEntity EntregaEntity = new EntregaEntity(StatusEntrega.PENDENTE, 1L, 1L);
            when(validaEntrega.run(EntregaDTO)).thenReturn(EntregaEntity);
            doNothing().when(buscaCliente).run(1L);
            doThrow(new NoSuchElementException("pedido não encontrado")).when(buscaPedido).run(1L);
            assertThatThrownBy(() -> controller.run(EntregaDTO))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("pedido não encontrado");
        }

        @Test
        @DisplayName("deve registrar entrega corretamente")
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
