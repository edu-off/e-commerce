package br.com.ms.cliente.adapters.controllers;

import br.com.ms.cliente.application.dto.ClienteDTO;
import br.com.ms.cliente.application.dto.EnderecoDTO;
import br.com.ms.cliente.application.exceptions.ClienteException;
import br.com.ms.cliente.application.exceptions.EnderecoException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
@AutoConfigureTestDatabase
public class AtualizacaoClienteControllerIT {

    @Autowired
    private AtualizacaoClienteController atualizacaoClienteController;

    @Autowired
    private CadastroClienteController cadastroClienteController;

    private ClienteDTO clienteDTOSetup;

    @BeforeEach
    public void setup() {
        EnderecoDTO enderecoDTO = new EnderecoDTO(null, "logradouro", "bairro", "cidade", "SP", 1L);
        ClienteDTO clienteDTO = new ClienteDTO(null, "nome", "ATIVO", "teste@teste.com.br", 1, 1L, enderecoDTO);
        clienteDTOSetup = cadastroClienteController.execute(clienteDTO);
    }

    @Nested
    public class Validacoes {

        @Test
        @DisplayName("Deve lançar exceção para cliente inexistente")
        public void deveLancarExcecaoParaClienteInexistente() {
            EnderecoDTO enderecoDTO = new EnderecoDTO(null, "logradouro", "bairro", "cidade", "SP", 1L);
            ClienteDTO clienteDTO = new ClienteDTO(null, "nome", "ATIVO", "teste@teste.com.br", 1, 1L, enderecoDTO);
            assertThatThrownBy(() -> atualizacaoClienteController.execute(0L,clienteDTO))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("cliente não encontrado");
        }

        @Test
        @DisplayName("Deve lançar exceção para cliente inválido")
        public void deveLancarExcecaoParaClienteInvalido() {
            EnderecoDTO enderecoDTO = new EnderecoDTO(null, "logradouro", "bairro", "cidade", "SP", 1L);
            ClienteDTO clienteDTO = new ClienteDTO(null, "", "ATIVO", "teste@teste.com.br", 1, 1L, enderecoDTO);
            assertThatThrownBy(() -> atualizacaoClienteController.execute(clienteDTOSetup.getId(), clienteDTO))
                    .isInstanceOf(ClienteException.class)
                    .hasMessage("erro ao validar cliente: nome inválido");
        }

        @Test
        @DisplayName("Deve lançar exceção para endereço inválido")
        public void deveLancarExcecaoParaEndereçoInvalido() {
            EnderecoDTO enderecoDTO = new EnderecoDTO(null, "", "bairro", "cidade", "SP", 1L);
            ClienteDTO clienteDTO = new ClienteDTO(null, "nome", "ATIVO", "teste@teste.com.br", 1, 1L, enderecoDTO);
            assertThatThrownBy(() -> atualizacaoClienteController.execute(clienteDTOSetup.getId(), clienteDTO))
                    .isInstanceOf(EnderecoException.class)
                    .hasMessage("erro ao validar endereço: logradouro inválido");
        }

    }

    @Nested
    public class SalvandoCliente {

        @Test
        @DisplayName("Deve atualizar cliente e endereço corretamente")
        public void deveAtualizarClienteEnderecoCorretamente() {
            EnderecoDTO enderecoDTO = new EnderecoDTO(null, "logradouro atualizado", "bairro", "cidade", "SP", 1L);
            ClienteDTO clienteDTO = new ClienteDTO(null, "nome atualizado", "ATIVO", "teste@teste.com.br", 1, 1L, enderecoDTO);
            ClienteDTO clienteDTOUpdated = atualizacaoClienteController.execute(clienteDTOSetup.getId(), clienteDTO);
            assertThat(clienteDTOUpdated).isInstanceOf(ClienteDTO.class).isNotNull();
            assertThat(clienteDTOUpdated.getId()).isEqualTo(clienteDTOSetup.getId());
            assertThat(clienteDTOUpdated.getNome()).isEqualTo(clienteDTO.getNome());
            assertThat(clienteDTOUpdated.getEndereco().getLogradouro()).isEqualTo(clienteDTO.getEndereco().getLogradouro());
        }

    }

}
