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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class AtualizaEntregadorTest {

    @Mock
    private EntregadorGateway entregadorGateway;

    @InjectMocks
    private AtualizaEntregador atualizaEntregador;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class AtualizandoEntregador {

        @Test
        @DisplayName("Deve atualizar entregador corretamente")
        public void deveAtualizarEntregadorCorretamente() {
            EntregadorEntity entregadorEntity = new EntregadorEntity(StatusEntregador.DISPONIVEL, "nome", "teste@teste.com.br");
            when(entregadorGateway.atualizar(1L, entregadorEntity)).thenReturn(entregadorEntity);
            EntregadorEntity entregadorEntityUpdated = atualizaEntregador.run(1L, entregadorEntity);
            assertThat(entregadorEntityUpdated).isInstanceOf(EntregadorEntity.class).isNotNull().isEqualTo(entregadorEntity);
        }

    }

}
