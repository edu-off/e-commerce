package br.com.ms.logistica.adapters.controllers;

import br.com.ms.logistica.application.exceptions.EntregaException;
import br.com.ms.logistica.application.usecases.AtualizaEntrega;
import br.com.ms.logistica.application.usecases.AtualizaEntregador;
import br.com.ms.logistica.application.usecases.BuscaEntrega;
import br.com.ms.logistica.application.usecases.ConcluiPedido;
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

public class ConcluiEntregaControllerTest {

    @Mock
    private BuscaEntrega buscaEntrega;

    @Mock
    private AtualizaEntrega atualizaEntrega;

    @Mock
    private ConcluiPedido concluiPedido;

    @Mock
    private AtualizaEntregador atualizaEntregador;

    @InjectMocks
    private ConcluiEntregaController concluiEntregaController;

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
            assertThatThrownBy(() -> concluiEntregaController.run(1L))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("entrega não encontrada");
        }

        @Test
        @DisplayName("deve lancar excecao para entrega cancelada")
        public void deveLancarExcecaoParaEntregaCancelada() {
            EntregaEntity entregaEntity =  new EntregaEntity(StatusEntrega.CANCELADA, 1L, 1L);
            when(buscaEntrega.run(1L)).thenReturn(entregaEntity);
            assertThatThrownBy(() -> concluiEntregaController.run(1L))
                    .isInstanceOf(EntregaException.class)
                    .hasMessage("entrega cancelada");
        }

        @Test
        @DisplayName("deve lancar excecao para entrega ja concluida")
        public void deveLancarExcecaoParaEntregaJaConcluida() {
            EntregaEntity entregaEntity =  new EntregaEntity(StatusEntrega.CONCLUIDA, 1L, 1L);
            when(buscaEntrega.run(1L)).thenReturn(entregaEntity);
            assertThatThrownBy(() -> concluiEntregaController.run(1L))
                    .isInstanceOf(EntregaException.class)
                    .hasMessage("entrega já está concluída");
        }

        @Test
        @DisplayName("deve lancar excecao para entrega pendende")
        public void deveLancarExcecaoParaEntregaPendente() {
            EntregaEntity entregaEntity =  new EntregaEntity(StatusEntrega.PENDENTE, 1L, 1L);
            when(buscaEntrega.run(1L)).thenReturn(entregaEntity);
            assertThatThrownBy(() -> concluiEntregaController.run(1L))
                    .isInstanceOf(EntregaException.class)
                    .hasMessage("entregas pendentes não podem ser concluidas");
        }

        @Test
        @DisplayName("deve lancar excecao para entrega nao encontrada na atualizacao")
        public void deveLancarExcecaoParaEntregaNaoEncontradaNaAtualizacao() {
            EntregaEntity entregaEntity =  new EntregaEntity(StatusEntrega.EM_TRANSITO, 1L, 1L);
            when(buscaEntrega.run(1L)).thenReturn(entregaEntity);
            when(atualizaEntrega.run(1L, entregaEntity)).thenThrow(new NoSuchElementException("entrega não encontrada"));
            assertThatThrownBy(() -> concluiEntregaController.run(1L))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("entrega não encontrada");
        }

        @Test
        @DisplayName("deve lancar excecao para pedido nao encontrado")
        public void deveLancarExcecaoParaPedidoNaoEncontrado() {
            EntregaEntity entregaEntity =  new EntregaEntity(StatusEntrega.EM_TRANSITO, 1L, 1L);
            when(buscaEntrega.run(1L)).thenReturn(entregaEntity);
            when(atualizaEntrega.run(1L, entregaEntity)).thenReturn(entregaEntity);
            doThrow(new NoSuchElementException("pedido não encontrado")).when(concluiPedido).run(entregaEntity.getPedidoId());
            assertThatThrownBy(() -> concluiEntregaController.run(1L))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("pedido não encontrado");
        }

        @Test
        @DisplayName("deve lancar excecao para entregador nao encontrado")
        public void deveLancarExcecaoParaEntregadorNaoEncontrado() {
            EntregaEntity entregaEntity =  new EntregaEntity(StatusEntrega.EM_TRANSITO, 1L, 1L);
            EntregadorEntity entregadorEntity = new EntregadorEntity(StatusEntregador.DISPONIVEL, "nome", "teste@teste.com.br");
            entregadorEntity.setId(1L);
            entregaEntity.setId(1L);
            entregaEntity.setEntregador(entregadorEntity);
            when(buscaEntrega.run(1L)).thenReturn(entregaEntity);
            when(atualizaEntrega.run(1L, entregaEntity)).thenReturn(entregaEntity);
            doNothing().when(concluiPedido).run(entregaEntity.getPedidoId());
            when(atualizaEntregador.run(1L, entregadorEntity)).thenThrow(new NoSuchElementException("entregador não encontrado"));
            assertThatThrownBy(() -> concluiEntregaController.run(1L))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("entregador não encontrado");
        }

        @Test
        @DisplayName("deve cancelar entrega corretamente")
        public void deveCancelarEntregaCorretamente() {
            EntregaEntity entregaEntity =  new EntregaEntity(StatusEntrega.EM_TRANSITO, 1L, 1L);
            EntregadorEntity entregadorEntity = new EntregadorEntity(StatusEntregador.DISPONIVEL, "nome", "teste@teste.com.br");
            entregadorEntity.setId(1L);
            entregaEntity.setId(1L);
            entregaEntity.setEntregador(entregadorEntity);
            when(buscaEntrega.run(1L)).thenReturn(entregaEntity);
            when(atualizaEntrega.run(1L, entregaEntity)).thenReturn(entregaEntity);
            doNothing().when(concluiPedido).run(entregaEntity.getPedidoId());
            when(atualizaEntregador.run(1L, entregadorEntity)).thenReturn(entregadorEntity);
            concluiEntregaController.run(1L);
            verify(buscaEntrega, times(1)).run(1L);
            verify(atualizaEntrega, times(1)).run(1L, entregaEntity);
            verify(concluiPedido, times(1)).run(entregaEntity.getPedidoId());
            verify(atualizaEntregador, times(1)).run(1L, entregadorEntity);
        }

    }

}
