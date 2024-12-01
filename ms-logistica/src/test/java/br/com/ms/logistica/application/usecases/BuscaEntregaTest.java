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
import org.springframework.stereotype.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@Service
public class BuscaEntregaTest {

    @Mock
    private EntregaGateway entregaGateway;

    @InjectMocks
    private BuscaEntrega buscaEntrega;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class BuscandoEntrega {

        @Test
        @DisplayName("deve buscar entrega corretamente")
        public void deveBuscarEntregaCorretamente() {
            EntregaEntity entregaEntity = new EntregaEntity(StatusEntrega.PENDENTE, 1L, 1L);
            when(entregaGateway.buscarPorId(1L)).thenReturn(entregaEntity);
            EntregaEntity entregaEntityReturned = buscaEntrega.run(1L);
            assertThat(entregaEntityReturned).isInstanceOf(EntregaEntity.class).isNotNull();
        }

    }

}
