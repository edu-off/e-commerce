package br.com.ms.cliente.adapters.gateways;

import br.com.ms.cliente.application.gateways.ClienteGateway;
import br.com.ms.cliente.domain.entities.ClienteEntity;
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
public class ClienteGatewayImplIT {

    @Autowired
    private ClienteGateway clienteGateway;

    private ClienteEntity clienteEntity1;

    @BeforeEach
    public void setUp() {
        clienteEntity1 = clienteGateway.salvarCliente(new ClienteEntity("nome 1", StatusCliente.ATIVO, "email1@email.com.br", 1, 1L));
        clienteGateway.salvarCliente(new ClienteEntity("teste 2", StatusCliente.ATIVO, "email2@email.com.br", 1, 1L));
        clienteGateway.salvarCliente(new ClienteEntity("teste 3", StatusCliente.INATIVO, "teste1@teste.com.br", 1, 1L));
        clienteGateway.salvarCliente(new ClienteEntity("nome 4", StatusCliente.INATIVO, "teste2@teste.com.br", 1, 1L));
    }

    @Nested
    public class SalvandoCliente {

        @Test
        @DisplayName("Deve salvar cliente corretamente")
        public void deveSalvarClienteCorretamente() {
            ClienteEntity clienteEntity = new ClienteEntity("nome", StatusCliente.ATIVO, "teste@teste.com.br", 1, 1L);
            ClienteEntity clienteEntityCreated = clienteGateway.salvarCliente(clienteEntity);
            assertThat(clienteEntityCreated).isInstanceOf(ClienteEntity.class).isNotNull();
            assertThat(clienteEntityCreated.getId()).isNotNull().isNotZero();
        }

    }

    @Nested
    public class AtualizandoCliente {

        @Test
        @DisplayName("Deve lançar exceçâo para cliente inexistente")
        public void deveLancarExcecaoParaClienteInexistente() {
            ClienteEntity clienteEntity = new ClienteEntity("nome", StatusCliente.ATIVO, "teste@teste.com.br", 1, 1L);
            assertThatThrownBy(() -> clienteGateway.atualizarCliente(0L, clienteEntity))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("cliente não encontrado");
        }

        @Test
        @DisplayName("Deve atualizar cliente corretamente")
        public void deveAtualizarClienteCorretamente() {
            ClienteEntity clienteEntity = new ClienteEntity("nome", StatusCliente.ATIVO, "teste@teste.com.br", 1, 1L);
            ClienteEntity clienteEntityUpdated = clienteGateway.atualizarCliente(clienteEntity1.getId(), clienteEntity);
            assertThat(clienteEntityUpdated).isInstanceOf(ClienteEntity.class).isNotNull();
            assertThat(clienteEntityUpdated.getId()).isEqualTo(clienteEntity1.getId());
            assertThat(clienteEntityUpdated.getNome()).isEqualTo(clienteEntity.getNome());
            assertThat(clienteEntityUpdated.getEmail()).isEqualTo(clienteEntity.getEmail());
        }

    }

    @Nested
    public class BuscandoClientes {

        @Test
        @DisplayName("Deve buscar cliente por id")
        public void deveBuscarClientePorId() {
            ClienteEntity clienteEntity = clienteGateway.buscaCliente(clienteEntity1.getId());
            assertThat(clienteEntity).isInstanceOf(ClienteEntity.class).isNotNull();
            assertThat(clienteEntity.getId()).isEqualTo(clienteEntity1.getId());
            assertThat(clienteEntity.getNome()).isEqualTo(clienteEntity1.getNome());
            assertThat(clienteEntity.getStatus()).isEqualTo(clienteEntity1.getStatus());
            assertThat(clienteEntity.getEmail()).isEqualTo(clienteEntity1.getEmail());
            assertThat(clienteEntity.getDdd()).isEqualTo(clienteEntity1.getDdd());
            assertThat(clienteEntity.getTelefone()).isEqualTo(clienteEntity1.getTelefone());
        }

        @Test
        @DisplayName("Deve buscar clientes por nome")
        public void deveBuscarClientesPorNome() {
            Page<ClienteEntity> clientes = clienteGateway.buscaClientePorNome("teste", PageRequest.of(0, 10));
            assertThat(clientes).isInstanceOf(Page.class).isNotNull().isNotEmpty().hasSize(2);
        }

        @Test
        @DisplayName("Deve buscar clientes por e-mail")
        public void deveBuscarClientesPorEmail() {
            Page<ClienteEntity> clientes = clienteGateway.buscaClientePorEmail("teste", PageRequest.of(0, 10));
            assertThat(clientes).isInstanceOf(Page.class).isNotNull().isNotEmpty().hasSize(2);
        }

        @Test
        @DisplayName("Deve buscar clientes por status")
        public void deveBuscarClientesPorStatus() {
            Page<ClienteEntity> clientes = clienteGateway.buscaClientePorStatus(StatusCliente.ATIVO, PageRequest.of(0, 10));
            assertThat(clientes).isInstanceOf(Page.class).isNotNull().isNotEmpty().hasSize(2);
        }

    }

}
