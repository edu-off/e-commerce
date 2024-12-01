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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class BuscaClientePorStatusTest {

    @Mock
    private ClienteGateway clienteGateway;

    @InjectMocks
    private BuscaClientePorStatus buscaClientePorStatus;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class BuscandoCliente {

        @Test
        @DisplayName("Deve buscar cliente corretamente")
        public void deveBuscarClienteCorretamente() {
            Page<ClienteEntity> clientes = Page.empty();
            when(clienteGateway.buscaClientePorStatus(StatusCliente.ATIVO, PageRequest.of(0, 10)))
                    .thenReturn(clientes);
            Page<ClienteEntity> clientesRetrieved = buscaClientePorStatus.execute("ATIVO", PageRequest.of(0, 10));
            assertThat(clientesRetrieved).isInstanceOf(Page.class).isNotNull().isEmpty();
        }

    }

}
