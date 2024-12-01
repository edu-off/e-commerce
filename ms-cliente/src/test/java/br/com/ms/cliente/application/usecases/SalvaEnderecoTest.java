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

public class SalvaEnderecoTest {

    @Mock
    private EnderecoGateway enderecoGateway;

    @InjectMocks
    private SalvaEndereco salvaEndereco;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class SalvandoEndereco {

        @Test
        @DisplayName("Deve salvar endereco corretamente")
        public void deveSalvarEnderecoCorretamente() {
            EnderecoEntity enderecoEntity = new EnderecoEntity("logradouro", "bairro", "cidade", "SP", 1L, new ClienteEntity());
            when(enderecoGateway.salvarEndereco(enderecoEntity)).thenReturn(enderecoEntity);
            EnderecoEntity enderecoEntityCreated = salvaEndereco.execute(enderecoEntity);
            assertThat(enderecoEntityCreated).isInstanceOf(EnderecoEntity.class).isNotNull().isEqualTo(enderecoEntity);
        }

    }

}
