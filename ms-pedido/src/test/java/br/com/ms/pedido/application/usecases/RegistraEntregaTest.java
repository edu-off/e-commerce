package br.com.ms.pedido.application.usecases;

import br.com.ms.pedido.application.dto.EntregaDTO;
import br.com.ms.pedido.application.gateways.EntregaGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class RegistraEntregaTest {

    @Mock
    private EntregaGateway entregaGateway;

    @InjectMocks
    private RegistraEntrega registraEntrega;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class RegistrandoEntrega {

        @Test
        @DisplayName("Deve registrar entrega corretamente")
        public void deveRegistrarEntregaCorretamente() {
            EntregaDTO entregaDTO = new EntregaDTO(null, "PENDENTE", 1L, 1L);
            when(entregaGateway.registraEntrega(any(EntregaDTO.class))).thenReturn(entregaDTO);
            EntregaDTO entregaDTOCreated = registraEntrega.run(1L, 1L);
            assertThat(entregaDTOCreated).isInstanceOf(EntregaDTO.class).isNotNull().isEqualTo(entregaDTO);
        }

    }

}
