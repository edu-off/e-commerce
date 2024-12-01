package br.com.ms.logistica.application.usecases;

import br.com.ms.logistica.application.gateways.EntregaGateway;
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
import static org.mockito.Mockito.when;

public class AtualizaEntregaTest {

    @Mock
    private EntregaGateway entregaGateway;

    @InjectMocks
    private AtualizaEntrega atualizaEntrega;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class AtualizandoEntrega {

        @Test
        @DisplayName("Deve atualizar entrega corretamente")
        public void deveAtualizarEntregaCorretamente() {
            EntregaEntity entregaEntity = new EntregaEntity(StatusEntrega.PENDENTE, 1L, 1L);
            when(entregaGateway.atualizar(1L, entregaEntity)).thenReturn(entregaEntity);
            EntregaEntity entregaEntityUpdated = atualizaEntrega.run(1L, entregaEntity);
            assertThat(entregaEntityUpdated).isInstanceOf(EntregaEntity.class).isNotNull().isEqualTo(entregaEntity);
        }

    }

}
