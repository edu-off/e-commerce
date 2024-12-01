package br.com.ms.cliente.adapters.gateways;

import br.com.ms.cliente.application.gateways.ClienteGateway;
import br.com.ms.cliente.application.gateways.EnderecoGateway;
import br.com.ms.cliente.domain.entities.ClienteEntity;
import br.com.ms.cliente.domain.entities.EnderecoEntity;
import br.com.ms.cliente.domain.enums.StatusCliente;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
@AutoConfigureTestDatabase
public class EnderecoGatewayImplIT {

    @Autowired
    private ClienteGateway clienteGateway;

    @Autowired
    private EnderecoGateway enderecoGateway;

    private ClienteEntity clienteEntitySetup;

    private EnderecoEntity enderecoEntitySetup;

    @BeforeEach
    public void setUp() {
        clienteEntitySetup = clienteGateway.salvarCliente(new ClienteEntity("nome", StatusCliente.ATIVO, "teste@teste.com.br", 1, 1L));
        enderecoEntitySetup = enderecoGateway.salvarEndereco(new EnderecoEntity("logradouro", "bairro", "cidade", "SP", 1L, clienteEntitySetup));
    }

    @Nested
    public class SalvandoEndereco {

        @Test
        @DisplayName("Deve salvar endereço corretamente")
        public void deveSalvarEnderecoCorretamente() {
            ClienteEntity clienteEntity = clienteGateway.salvarCliente(new ClienteEntity("nome", StatusCliente.ATIVO, "teste@teste.com.br", 1, 1L));
            EnderecoEntity enderecoEntity = new EnderecoEntity("logradouro", "bairro", "cidade", "SP", 1L, clienteEntity);
            EnderecoEntity enderecoEntityCreated = enderecoGateway.salvarEndereco(enderecoEntity);
            assertThat(enderecoEntityCreated).isInstanceOf(EnderecoEntity.class).isNotNull();
            assertThat(enderecoEntityCreated.getId()).isNotNull().isNotZero();
        }

    }

    @Nested
    public class AtualizandoEndereco {

        @Test
        @DisplayName("Deve lançar exceçâo para cliente inexistente")
        public void deveLancarExcecaoParaClienteInexistente() {
            EnderecoEntity enderecoEntity = new EnderecoEntity("logradouro atualizado", "bairro", "cidade", "SP", 1L, clienteEntitySetup);
            assertThatThrownBy(() -> enderecoGateway.atualizarEndereco(0L, enderecoEntity))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("endereço não encontrado");
        }

        @Test
        @DisplayName("Deve atualizar cliente corretamente")
        public void deveAtualizarClienteCorretamente() {
            EnderecoEntity enderecoEntity = new EnderecoEntity("logradouro atualizado", "bairro", "cidade", "SP", 1L, clienteEntitySetup);
            EnderecoEntity enderecoEntityUpdated = enderecoGateway.atualizarEndereco(enderecoEntitySetup.getId(), enderecoEntity);
            assertThat(enderecoEntityUpdated).isInstanceOf(EnderecoEntity.class).isNotNull();
            assertThat(enderecoEntityUpdated.getId()).isEqualTo(enderecoEntitySetup.getId());
            assertThat(enderecoEntityUpdated.getLogradouro()).isEqualTo(enderecoEntity.getLogradouro());
        }

    }

    @Nested
    public class BuscandoClientes {

        @Test
        @DisplayName("Deve lançar exceção para endereço inexistente")
        public void deveLancarExcecaoParaEnderecoInexistente() {
            assertThatThrownBy(() -> enderecoGateway.buscaEnderecoPorCliente(0L))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("endereço não encontrado");
        }

        @Test
        @DisplayName("Deve buscar cliente por id")
        public void deveBuscarClientePorId() {
            EnderecoEntity enderecoEntity = enderecoGateway.buscaEnderecoPorCliente(clienteEntitySetup.getId());
            assertThat(enderecoEntity).isInstanceOf(EnderecoEntity.class).isNotNull();
            assertThat(enderecoEntity.getId()).isEqualTo(enderecoEntitySetup.getId());
            assertThat(enderecoEntity.getLogradouro()).isEqualTo(enderecoEntitySetup.getLogradouro());
            assertThat(enderecoEntity.getBairro()).isEqualTo(enderecoEntitySetup.getBairro());
            assertThat(enderecoEntity.getCidade()).isEqualTo(enderecoEntitySetup.getCidade());
            assertThat(enderecoEntity.getUf()).isEqualTo(enderecoEntitySetup.getUf());
            assertThat(enderecoEntity.getCep()).isEqualTo(enderecoEntitySetup.getCep());
            assertThat(enderecoEntity.getCliente()).isInstanceOf(ClienteEntity.class).isNotNull();
        }

    }

}
