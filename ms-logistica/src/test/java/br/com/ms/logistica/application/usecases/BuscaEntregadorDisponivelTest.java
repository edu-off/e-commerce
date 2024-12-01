package br.com.ms.logistica.application.usecases;

import br.com.ms.logistica.application.gateways.EntregadorGateway;
import br.com.ms.logistica.domain.entities.EntregadorEntity;
import br.com.ms.logistica.domain.enums.StatusEntregador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@Service
public class BuscaEntregadorDisponivelTest {

    @Mock
    private EntregadorGateway entregadorGateway;

    @InjectMocks
    private BuscaEntregadorDisponivel buscaEntregadorDisponivel;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class BuscandoEntregadorDisponivel {

        @Test
        @DisplayName("deve buscar entregador disponivel corretamente")
        public void deveBuscarEntregadorDisponivelCorretamente() {
            EntregadorEntity entregador1 = new EntregadorEntity(StatusEntregador.DISPONIVEL, "nome1", "teste@teste.com.br");
            EntregadorEntity entregador2 = new EntregadorEntity(StatusEntregador.DISPONIVEL, "nome2", "teste@teste.com.br");
            EntregadorEntity entregador3 = new EntregadorEntity(StatusEntregador.DISPONIVEL, "nome3", "teste@teste.com.br");
            List<EntregadorEntity> entregadores = List.of(entregador1, entregador2, entregador3);
            when(entregadorGateway.buscarPorStatus(StatusEntregador.DISPONIVEL)).thenReturn(entregadores);
            EntregadorEntity entregador = buscaEntregadorDisponivel.run();
            assertThat(entregador).isInstanceOf(EntregadorEntity.class).isNotNull();
        }

        @Test
        @DisplayName("nao deve encontrar entregador disponivel")
        public void naoDeveEncontrarEntregadorDisponivel() {
            List<EntregadorEntity> entregadores = new ArrayList<>();
            when(entregadorGateway.buscarPorStatus(StatusEntregador.DISPONIVEL)).thenReturn(entregadores);
            EntregadorEntity entregador = buscaEntregadorDisponivel.run();
            assertThat(entregador).isNull();
        }

    }

}
