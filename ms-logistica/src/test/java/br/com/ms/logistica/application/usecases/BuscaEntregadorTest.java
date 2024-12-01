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
public class BuscaEntregadorTest {

    @Mock
    private EntregadorGateway entregadorGateway;

    @InjectMocks
    private BuscaEntregador buscaEntregador;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class BuscandoEntregador {

        @Test
        @DisplayName("deve buscar entregador corretamente")
        public void deveBuscarEntregadorCorretamente() {
            EntregadorEntity entregadorEntity = new EntregadorEntity(StatusEntregador.DISPONIVEL, "nome", "teste@teste.com.br");
            when(entregadorGateway.buscarPorId(1L)).thenReturn(entregadorEntity);
            EntregadorEntity entregadorEntityReturned = buscaEntregador.run(1L);
            assertThat(entregadorEntityReturned).isInstanceOf(EntregadorEntity.class).isNotNull();
        }

    }

}
