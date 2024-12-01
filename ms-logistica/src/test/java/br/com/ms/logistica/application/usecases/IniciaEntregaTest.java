package br.com.ms.logistica.application.usecases;

import br.com.ms.logistica.application.gateways.EntregaGateway;
import br.com.ms.logistica.application.gateways.EntregadorGateway;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.mockito.Mockito.*;

@Service
public class IniciaEntregaTest {

    @Mock
    private EntregaGateway entregaGateway;

    @Mock
    private EntregadorGateway entregadorGateway;

    @InjectMocks
    private IniciaEntrega iniciaEntrega;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class IniciandoEntrega {

        @Test
        @DisplayName("deve iniciar entrega corretamente")
        public void deveIniciarEntregaCorretamente() {
            EntregaEntity entregaEntity = new EntregaEntity(StatusEntrega.PENDENTE, 1L, 1L);
            EntregadorEntity entregadorEntity = new EntregadorEntity(StatusEntregador.DISPONIVEL, "nome", "teste@teste.com.br");
            entregaEntity.setId(1L);
            entregadorEntity.setId(1L);

            EntregaEntity entregaEntityUpdated = new EntregaEntity(StatusEntrega.EM_TRANSITO, 1L, 1L);
            EntregadorEntity entregadorEntityUpdated = new EntregadorEntity(StatusEntregador.EM_TRANSITO, "nome", "teste@teste.com.br");
            entregaEntityUpdated.setId(1L);
            entregadorEntityUpdated.setId(1L);

            when(entregaGateway.atualizar(1L, entregaEntity)).thenReturn(entregaEntityUpdated);
            when(entregadorGateway.atualizar(1L, entregadorEntity)).thenReturn(entregadorEntityUpdated);

            iniciaEntrega.run(entregaEntity, entregadorEntity);

            verify(entregaGateway, times(1)).atualizar(1L, entregaEntity);
            verify(entregadorGateway, times(1)).atualizar(1L, entregadorEntity);
       }

    }

}
