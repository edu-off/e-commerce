package br.com.ms.cliente.adapters.controllers;

import br.com.ms.cliente.application.dto.ClienteDTO;
import br.com.ms.cliente.application.dto.EnderecoDTO;
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
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@AutoConfigureTestDatabase
public class BuscaClienteControllerIT {

    @Autowired
    private BuscaClienteController buscaClienteController;

    @Autowired
    private CadastroClienteController cadastroClienteController;

    private ClienteDTO clienteDTOSetup;

    @BeforeEach
    public void setup() {
        EnderecoDTO enderecoDTO = new EnderecoDTO(null, "logradouro", "bairro", "cidade", "SP", 1L);
        ClienteDTO clienteDTO1 = new ClienteDTO(null, "teste 1", "ATIVO", "teste1@teste.com.br", 1, 1L, enderecoDTO);
        ClienteDTO clienteDTO2 = new ClienteDTO(null, "teste 2", "INATIVO", "teste2@teste.com.br", 1, 1L, enderecoDTO);
        ClienteDTO clienteDTO3 = new ClienteDTO(null, "nome 1", "INATIVO", "email1@teste.com.br", 1, 1L, enderecoDTO);
        ClienteDTO clienteDTO4 = new ClienteDTO(null, "nome 2", "ATIVO", "email2@teste.com.br", 1, 1L, enderecoDTO);
        clienteDTOSetup = cadastroClienteController.execute(clienteDTO1);
        cadastroClienteController.execute(clienteDTO2);
        cadastroClienteController.execute(clienteDTO3);
        cadastroClienteController.execute(clienteDTO4);
    }

    @Nested
    public class Buscas {

        @Test
        @DisplayName("deve buscar cliente por id")
        public void deveBuscarClientePorId() {
            ClienteDTO clienteDTORetrieved = buscaClienteController.porId(clienteDTOSetup.getId());
            assertThat(clienteDTORetrieved).isInstanceOf(ClienteDTO.class).isNotNull();
            assertThat(clienteDTORetrieved.getId()).isNotZero().isEqualTo(clienteDTOSetup.getId());
        }

        @Test
        @DisplayName("deve buscar clientes por nome")
        public void deveBuscarClientesPorNome() {
            Pageable pageable = PageRequest.of(0, 10);
            Page<ClienteDTO> clientesDTORetrieved = buscaClienteController.porNome("nome", pageable);
            assertThat(clientesDTORetrieved).isInstanceOf(Page.class).isNotEmpty().hasSize(2);
        }


        @Test
        @DisplayName("deve buscar clientes por e-mail")
        public void deveBuscarClientesPorEmail() {
            Pageable pageable = PageRequest.of(0, 10);
            Page<ClienteDTO> clientesDTORetrieved = buscaClienteController.porEmail("email", pageable);
            assertThat(clientesDTORetrieved).isInstanceOf(Page.class).isNotEmpty().hasSize(2);
        }

        @Test
        @DisplayName("deve buscar clientes por status")
        public void deveBuscarClientesPorStatus() {
            Pageable pageable = PageRequest.of(0, 10);
            Page<ClienteDTO> clientesDTORetrieved = buscaClienteController.porStatus(StatusCliente.ATIVO.toString(), pageable);
            assertThat(clientesDTORetrieved).isInstanceOf(Page.class).isNotEmpty().hasSize(2);
        }

    }

}
