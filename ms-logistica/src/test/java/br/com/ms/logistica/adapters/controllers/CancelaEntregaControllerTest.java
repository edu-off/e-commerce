package br.com.ms.logistica.adapters.controllers;

import br.com.ms.logistica.application.exceptions.EntregaException;
import br.com.ms.logistica.application.usecases.AtualizaEntrega;
import br.com.ms.logistica.application.usecases.AtualizaEntregador;
import br.com.ms.logistica.application.usecases.BuscaEntrega;
import br.com.ms.logistica.application.usecases.CancelaPedido;
import br.com.ms.logistica.domain.entities.EntregaEntity;
import br.com.ms.logistica.domain.entities.EntregadorEntity;
import br.com.ms.logistica.domain.enums.StatusEntrega;
import br.com.ms.logistica.domain.enums.StatusEntregador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class CancelaEntregaControllerTest {

    @Mock
    private BuscaEntrega buscaEntrega;

    @Mock
    private AtualizaEntrega atualizaEntrega;

    @Mock
    private CancelaPedido cancelaPedido;

    @Mock
    private AtualizaEntregador atualizaEntregador;

    @InjectMocks
    private CancelaEntregaController cancelaEntregaController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class CancelandoEntrega {

        @Test
        @DisplayName("deve lancar excecao para entrega nao encontrada na busca")
        public void deveLancarExcecaoParaEntregaNaoEncontradaNaBusca() {
            when(buscaEntrega.run(1L)).thenThrow(new NoSuchElementException("entrega não encontrada"));
            assertThatThrownBy(() -> cancelaEntregaController.run(1L))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("entrega não encontrada");
        }

        @Test
        @DisplayName("deve lancar excecao para entrega já cancelada")
        public void deveLancarExcecaoParaEntregaJaCancelada() {
            EntregaEntity entregaEntity =  new EntregaEntity(StatusEntrega.CANCELADA, 1L, 1L);
            when(buscaEntrega.run(1L)).thenReturn(entregaEntity);
            assertThatThrownBy(() -> cancelaEntregaController.run(1L))
                    .isInstanceOf(EntregaException.class)
                    .hasMessage("entrega já está cancelada");
        }

        @Test
        @DisplayName("deve lancar excecao para entrega concluida")
        public void deveLancarExcecaoParaEntregaConcluida() {
            EntregaEntity entregaEntity =  new EntregaEntity(StatusEntrega.CONCLUIDA, 1L, 1L);
            when(buscaEntrega.run(1L)).thenReturn(entregaEntity);
            assertThatThrownBy(() -> cancelaEntregaController.run(1L))
                    .isInstanceOf(EntregaException.class)
                    .hasMessage("entrega já está concluída");
        }

        @Test
        @DisplayName("deve lancar excecao para entrega em transito")
        public void deveLancarExcecaoParaEntregaEmTransito() {
            EntregaEntity entregaEntity =  new EntregaEntity(StatusEntrega.EM_TRANSITO, 1L, 1L);
            when(buscaEntrega.run(1L)).thenReturn(entregaEntity);
            assertThatThrownBy(() -> cancelaEntregaController.run(1L))
                    .isInstanceOf(EntregaException.class)
                    .hasMessage("entrega em trânsito não pode ser cancelada");
        }

        @Test
        @DisplayName("deve lancar excecao para entrega nao encontrada na atualizacao")
        public void deveLancarExcecaoParaEntregaNaoEncontradaNaAtualizacao() {
            EntregaEntity entregaEntity =  new EntregaEntity(StatusEntrega.PENDENTE, 1L, 1L);
            when(buscaEntrega.run(1L)).thenReturn(entregaEntity);
            when(atualizaEntrega.run(1L, entregaEntity)).thenThrow(new NoSuchElementException("entrega não encontrada"));
            assertThatThrownBy(() -> cancelaEntregaController.run(1L))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("entrega não encontrada");
        }

        @Test
        @DisplayName("deve lancar excecao para pedido nao encontrado")
        public void deveLancarExcecaoParaPedidoNaoEncontrado() {
            EntregaEntity entregaEntity =  new EntregaEntity(StatusEntrega.PENDENTE, 1L, 1L);
            when(buscaEntrega.run(1L)).thenReturn(entregaEntity);
            when(atualizaEntrega.run(1L, entregaEntity)).thenReturn(entregaEntity);
            doThrow(new NoSuchElementException("pedido não encontrado")).when(cancelaPedido).run(entregaEntity.getPedidoId());
            assertThatThrownBy(() -> cancelaEntregaController.run(1L))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("pedido não encontrado");
        }

        @Test
        @DisplayName("deve lancar excecao para entregador nao encontrado")
        public void deveLancarExcecaoParaEntregadorNaoEncontrado() {
            EntregaEntity entregaEntity =  new EntregaEntity(StatusEntrega.PENDENTE, 1L, 1L);
            EntregadorEntity entregadorEntity = new EntregadorEntity(StatusEntregador.DISPONIVEL, "nome", "teste@teste.com.br");
            entregadorEntity.setId(1L);
            entregaEntity.setId(1L);
            entregaEntity.setEntregador(entregadorEntity);
            when(buscaEntrega.run(1L)).thenReturn(entregaEntity);
            when(atualizaEntrega.run(1L, entregaEntity)).thenReturn(entregaEntity);
            doNothing().when(cancelaPedido).run(entregaEntity.getPedidoId());
            when(atualizaEntregador.run(1L, entregadorEntity)).thenThrow(new NoSuchElementException("entregador não encontrado"));
            assertThatThrownBy(() -> cancelaEntregaController.run(1L))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("entregador não encontrado");
        }

        @Test
        @DisplayName("deve cancelar entrega corretamente")
        public void deveCancelarEntregaCorretamente() {
            EntregaEntity entregaEntity =  new EntregaEntity(StatusEntrega.PENDENTE, 1L, 1L);
            EntregadorEntity entregadorEntity = new EntregadorEntity(StatusEntregador.DISPONIVEL, "nome", "teste@teste.com.br");
            entregadorEntity.setId(1L);
            entregaEntity.setId(1L);
            entregaEntity.setEntregador(entregadorEntity);
            when(buscaEntrega.run(1L)).thenReturn(entregaEntity);
            when(atualizaEntrega.run(1L, entregaEntity)).thenReturn(entregaEntity);
            doNothing().when(cancelaPedido).run(entregaEntity.getPedidoId());
            when(atualizaEntregador.run(1L, entregadorEntity)).thenReturn(entregadorEntity);
            cancelaEntregaController.run(1L);
            verify(buscaEntrega, times(1)).run(1L);
            verify(atualizaEntrega, times(1)).run(1L, entregaEntity);
            verify(cancelaPedido, times(1)).run(entregaEntity.getPedidoId());
            verify(atualizaEntregador, times(1)).run(1L, entregadorEntity);
        }

    }

}
