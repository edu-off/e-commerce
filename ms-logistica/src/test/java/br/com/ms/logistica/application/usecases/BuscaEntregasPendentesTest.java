package br.com.ms.logistica.application.usecases;

import br.com.ms.logistica.application.gateways.EntregaGateway;
import br.com.ms.logistica.application.gateways.PedidoGateway;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Service
public class BuscaEntregasPendentesTest {

    @Mock
    private EntregaGateway entregaGateway;

    @InjectMocks
    private BuscaEntregasPendentes buscaEntregasPendentes;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class BuscandoEntregasPendentes {

        @Test
        @DisplayName("deve buscar entregas pendentes corretamente")
        public void deveBuscarEntregasPendentesCorretamente() {
            List<EntregaEntity> entregas = List.of(new EntregaEntity(), new EntregaEntity(), new EntregaEntity());
            when(entregaGateway.buscarPorStatus(StatusEntrega.PENDENTE)).thenReturn(entregas);
            List<EntregaEntity> entregasReturned = buscaEntregasPendentes.run();
            assertThat(entregasReturned).isInstanceOf(List.class).isNotEmpty().hasSize(3);
        }

    }

}
