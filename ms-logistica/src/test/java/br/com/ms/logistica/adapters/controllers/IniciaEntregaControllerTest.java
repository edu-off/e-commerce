package br.com.ms.logistica.adapters.controllers;

import br.com.ms.logistica.application.usecases.BuscaEntregadorDisponivel;
import br.com.ms.logistica.application.usecases.BuscaEntregasPendentes;
import br.com.ms.logistica.application.usecases.IniciaEntrega;
import br.com.ms.logistica.application.usecases.RecuperaDadosCliente;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class IniciaEntregaControllerTest {

    @Mock
    private BuscaEntregasPendentes buscaEntregasPendentes;

    @Mock
    private BuscaEntregadorDisponivel buscaEntregadorDisponivel;

    @Mock
    private RecuperaDadosCliente recuperaDadosCliente;

    @Mock
    private IniciaEntrega iniciaEntrega;

    @InjectMocks
    private IniciaEntregaController iniciaEntregaController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class IniciandoEntregas {

        @Test
        @DisplayName("nao deve haver entregas pendentes")
        public void naoDeveHaverEntregasPendentes() {
            when(buscaEntregasPendentes.run()).thenReturn(new ArrayList<>());
            iniciaEntregaController.run();
            verify(buscaEntregasPendentes, times(1)).run();
        }

        @Test
        @DisplayName("nao deve haver entregadores disponiveis")
        public void naoDeveHaverEntregadoresDisponiveis() {
            when(buscaEntregasPendentes.run()).thenReturn(List.of(new EntregaEntity(StatusEntrega.PENDENTE, 1L, 1L)));
            when(buscaEntregadorDisponivel.run()).thenReturn(null);
            iniciaEntregaController.run();
            verify(buscaEntregasPendentes, times(1)).run();
            verify(buscaEntregadorDisponivel, times(1)).run();
        }

        @Test
        @DisplayName("deve iniciar entregas pendentes")
        public void deveIniciarEntregasPendentes() {
            EntregaEntity entregaEntity = new EntregaEntity(StatusEntrega.PENDENTE, 1L, 1L);
            EntregadorEntity entregadorEntity = new EntregadorEntity(StatusEntregador.DISPONIVEL, "nome", "teste@teste.com.br");
            entregadorEntity.setId(1L);
            entregaEntity.setId(1L);
            entregaEntity.setEntregador(entregadorEntity);
            when(buscaEntregasPendentes.run()).thenReturn(List.of(entregaEntity));
            when(buscaEntregadorDisponivel.run()).thenReturn(entregadorEntity);
            when(recuperaDadosCliente.porId(entregaEntity)).thenReturn(entregaEntity);
            doNothing().when(iniciaEntrega).run(entregaEntity, entregadorEntity);
            iniciaEntregaController.run();
            verify(buscaEntregasPendentes, times(1)).run();
            verify(buscaEntregadorDisponivel, times(1)).run();
            verify(recuperaDadosCliente, times(1)).porId(entregaEntity);
            verify(iniciaEntrega, times(1)).run(entregaEntity, entregadorEntity);
        }

    }

}
