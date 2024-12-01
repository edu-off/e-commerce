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

public class BuscaClienteTest {

    @Mock
    private ClienteGateway clienteGateway;

    @InjectMocks
    private BuscaCliente buscaCliente;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class BuscandoCliente {

        @Test
        @DisplayName("Deve buscar cliente corretamente")
        public void deveBuscarClienteCorretamente() {
            ClienteEntity clienteEntity = new ClienteEntity("nome", StatusCliente.ATIVO, "teste@teste.com.br", 1, 1L);
            when(clienteGateway.buscaCliente(1L)).thenReturn(clienteEntity);
            ClienteEntity clienteEntityRetrieved = buscaCliente.execute(1L);
            assertThat(clienteEntityRetrieved).isInstanceOf(ClienteEntity.class).isNotNull().isEqualTo(clienteEntity);
        }

    }

}
