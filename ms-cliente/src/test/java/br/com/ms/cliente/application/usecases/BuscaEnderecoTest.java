package br.com.ms.cliente.application.usecases;

import br.com.ms.cliente.application.gateways.EnderecoGateway;
import br.com.ms.cliente.domain.entities.ClienteEntity;
import br.com.ms.cliente.domain.entities.EnderecoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class BuscaEnderecoTest {

    @Mock
    private EnderecoGateway enderecoGateway;

    @InjectMocks
    private BuscaEndereco buscaEndereco;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class BuscandoEndereco {

        @Test
        @DisplayName("Deve buscar endereço corretamente")
        public void deveBuscarEnderecoCorretamente() {
            EnderecoEntity enderecoEntity = new EnderecoEntity("logradouro", "bairro", "cidade", "SP", 1L, new ClienteEntity());
            when(enderecoGateway.buscaEnderecoPorCliente(1L)).thenReturn(enderecoEntity);
            EnderecoEntity enderecoEntityRetrieved = buscaEndereco.execute(1L);
            assertThat(enderecoEntityRetrieved).isInstanceOf(EnderecoEntity.class).isNotNull().isEqualTo(enderecoEntity);
        }

    }

}
