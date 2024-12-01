package br.com.ms.cliente.application.usecases;

import br.com.ms.cliente.application.gateways.ClienteGateway;
import br.com.ms.cliente.domain.entities.ClienteEntity;
import br.com.ms.cliente.domain.enums.StatusCliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class AtualizaClienteTest {

    @Mock
    private ClienteGateway clienteGateway;

    @InjectMocks
    private AtualizaCliente atualizaCliente;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class AtualizandoCliente {

        @Test
        @DisplayName("Deve atualizar cliente corretamente")
        public void deveAtualizarClienteCorretamente() {
            ClienteEntity clienteEntity = new ClienteEntity("nome", StatusCliente.ATIVO, "teste@teste.com.br", 1, 1L);
            when(clienteGateway.atualizarCliente(1L, clienteEntity)).thenReturn(clienteEntity);
            ClienteEntity clienteEntityUpdated = atualizaCliente.execute(1L, clienteEntity);
            assertThat(clienteEntityUpdated).isInstanceOf(ClienteEntity.class).isNotNull().isEqualTo(clienteEntity);
        }

    }

}
