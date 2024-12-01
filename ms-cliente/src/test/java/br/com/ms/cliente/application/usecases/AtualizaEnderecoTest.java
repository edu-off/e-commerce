package br.com.ms.cliente.application.usecases;

import br.com.ms.cliente.application.gateways.ClienteGateway;
import br.com.ms.cliente.application.gateways.EnderecoGateway;
import br.com.ms.cliente.domain.entities.ClienteEntity;
import br.com.ms.cliente.domain.entities.EnderecoEntity;
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

public class AtualizaEnderecoTest {

    @Mock
    private EnderecoGateway enderecoGateway;

    @InjectMocks
    private AtualizaEndereco atualizaEndereco;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class AtualizandoEndereco {

        @Test
        @DisplayName("Deve atualizar endereço corretamente")
        public void deveAtualizarEnderecoCorretamente() {
            EnderecoEntity enderecoEntity = new EnderecoEntity("logradouro", "bairro", "cidade", "SP", 1L, new ClienteEntity());
            when(enderecoGateway.atualizarEndereco(1L, enderecoEntity)).thenReturn(enderecoEntity);
            EnderecoEntity enderecoEntityUpdated = atualizaEndereco.execute(1L, enderecoEntity);
            assertThat(enderecoEntityUpdated).isInstanceOf(EnderecoEntity.class).isNotNull().isEqualTo(enderecoEntity);
        }

    }

}
