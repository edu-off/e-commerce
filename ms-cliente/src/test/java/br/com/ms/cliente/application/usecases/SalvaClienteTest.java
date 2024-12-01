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

public class SalvaClienteTest {

    @Mock
    private ClienteGateway clienteGateway;

    @InjectMocks
    private SalvaCliente salvaCliente;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class SalvandoCliente {

        @Test
        @DisplayName("Deve salvar cliente corretamente")
        public void deveSalvarClienteCorretamente() {
            ClienteEntity clienteEntity = new ClienteEntity("nome", StatusCliente.ATIVO, "teste@teste.com.br", 1, 1L);
            when(clienteGateway.salvarCliente(clienteEntity)).thenReturn(clienteEntity);
            ClienteEntity clienteEntityCreated = salvaCliente.execute(clienteEntity);
            assertThat(clienteEntityCreated).isInstanceOf(ClienteEntity.class).isNotNull().isEqualTo(clienteEntity);
        }

    }

}
