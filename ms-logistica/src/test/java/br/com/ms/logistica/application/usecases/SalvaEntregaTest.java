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

public class SalvaEntregaTest {

    @Mock
    private EntregaGateway entregaGateway;

    @InjectMocks
    private SalvaEntrega salvaEntrega;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class SalvandoEntrega {

        @Test
        @DisplayName("Deve salvar entrega corretamente")
        public void deveSalvarEntregaCorretamente() {
            EntregaEntity entregaEntity = new EntregaEntity(StatusEntrega.PENDENTE, 1L, 1L);
            when(entregaGateway.salvar(entregaEntity)).thenReturn(entregaEntity);
            EntregaEntity entregaEntityCreated = salvaEntrega.run(entregaEntity);
            assertThat(entregaEntityCreated).isInstanceOf(EntregaEntity.class).isNotNull().isEqualTo(entregaEntity);
        }

    }

}
