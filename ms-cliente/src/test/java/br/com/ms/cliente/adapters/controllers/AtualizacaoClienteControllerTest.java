package br.com.ms.cliente.adapters.controllers;

import br.com.ms.cliente.adapters.presenters.ClientePresenter;
import br.com.ms.cliente.application.dto.ClienteDTO;
import br.com.ms.cliente.application.dto.EnderecoDTO;
import br.com.ms.cliente.application.exceptions.ClienteException;
import br.com.ms.cliente.application.exceptions.EnderecoException;
import br.com.ms.cliente.application.usecases.*;
import br.com.ms.cliente.domain.entities.ClienteEntity;
import br.com.ms.cliente.domain.entities.EnderecoEntity;
import br.com.ms.cliente.domain.enums.StatusCliente;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.exceptions.base.MockitoAssertionError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AtualizacaoClienteControllerTest {

    @Mock
    private ValidacaoCliente validacaoCliente;

    @Mock
    private ValidacaoEndereco validacaoEndereco;

    @Mock
    private BuscaCliente buscaCliente;

    @Mock
    private BuscaEndereco buscaEndereco;

    @Mock
    private AtualizaCliente atualizaCliente;

    @Mock
    private AtualizaEndereco atualizaEndereco;

    @Mock
    private ClientePresenter presenter;

    @InjectMocks
    private AtualizacaoClienteController atualizacaoClienteController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class Validacoes {

        @Test
        @DisplayName("Deve lançar exceção para cliente inexistente")
        public void deveLancarExcecaoParaClienteInexistente() {
            EnderecoDTO enderecoDTO = new EnderecoDTO(null, "logradouro", "bairro", "cidade", "SP", 1L);
            ClienteDTO clienteDTO = new ClienteDTO(null, "nome", "ATIVO", "teste@teste.com.br", 1, 1L, enderecoDTO);
            Mockito.when(buscaCliente.execute(clienteDTO.getId())).thenReturn(null);
            assertThatThrownBy(() ->atualizacaoClienteController.execute(1L, clienteDTO))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("cliente não encontrado");
        }

        @Test
        @DisplayName("Deve lançar exceção para cliente inválido")
        public void deveLancarExcecaoParaClienteInvalido() {
            EnderecoDTO enderecoDTO = new EnderecoDTO(null, "logradouro", "bairro", "cidade", "SP", 1L);
            ClienteDTO clienteDTO = new ClienteDTO(1L, "", "ATIVO", "teste@teste.com.br", 1, 1L, enderecoDTO);
            ClienteEntity clienteEntity = new ClienteEntity("nome", StatusCliente.ATIVO, "teste@teste.com.br", 1, 1L);

            Mockito.when(buscaCliente.execute(clienteDTO.getId())).thenReturn(clienteEntity);
            Mockito.when(validacaoCliente.execute(clienteDTO)).thenThrow(new ClienteException("nome inválido"));

            assertThatThrownBy(() ->atualizacaoClienteController.execute(1L, clienteDTO))
                    .isInstanceOf(ClienteException.class)
                    .hasMessage("nome inválido");
        }

        @Test
        @DisplayName("Deve lançar exceção para endereço inválido")
        public void deveLancarExcecaoParaEndereçoInvalido() {
            EnderecoDTO enderecoDTO = new EnderecoDTO(null, "", "bairro", "cidade", "SP", 1L);
            ClienteDTO clienteDTO = new ClienteDTO(1L, "nome", "ATIVO", "teste@teste.com.br", 1, 1L, null);
            ClienteEntity clienteEntity = new ClienteEntity("nome", StatusCliente.ATIVO, "teste@teste.com.br", 1, 1L);

            Mockito.when(buscaCliente.execute(clienteDTO.getId())).thenReturn(clienteEntity);
            Mockito.when(validacaoCliente.execute(clienteDTO)).thenReturn(clienteEntity);
            Mockito.when(validacaoEndereco.execute(clienteDTO.getEndereco(), clienteEntity)).thenThrow(new EnderecoException("endereço inválido"));

            assertThatThrownBy(() ->atualizacaoClienteController.execute(1L, clienteDTO))
                    .isInstanceOf(EnderecoException.class)
                    .hasMessage("endereço inválido");
        }

    }

    @Nested
    public class SalvandoCliente {

        @Test
        @DisplayName("Deve atualizar cliente e endereço corretamente")
        public void deveAtualizarClienteEnderecoCorretamente() {
            EnderecoDTO enderecoDTO = new EnderecoDTO(1L, "logradouro", "bairro", "cidade", "SP", 1L);
            ClienteDTO clienteDTO = new ClienteDTO(1L, "nome", "ATIVO", "teste@teste.com.br", 1, 1L, enderecoDTO);
            ClienteEntity clienteEntity = new ClienteEntity("nome", StatusCliente.ATIVO, "teste@teste.com.br", 1, 1L);
            EnderecoEntity enderecoEntity = new EnderecoEntity("logradouro", "bairro", "cidade", "SP", 1L, clienteEntity);
            enderecoEntity.setId(1L);

            Mockito.when(buscaCliente.execute(clienteDTO.getId())).thenReturn(clienteEntity);
            Mockito.when(buscaEndereco.execute(clienteDTO.getId())).thenReturn(enderecoEntity);
            Mockito.when(validacaoCliente.execute(clienteDTO)).thenReturn(clienteEntity);
            Mockito.when(validacaoEndereco.execute(clienteDTO.getEndereco(), clienteEntity)).thenReturn(enderecoEntity);
            Mockito.when(atualizaCliente.execute(1L, clienteEntity)).thenReturn(clienteEntity);
            Mockito.when(atualizaEndereco.execute(1L, enderecoEntity)).thenReturn(enderecoEntity);
            Mockito.when(presenter.transform(clienteEntity, enderecoEntity)).thenReturn(clienteDTO);

            ClienteDTO clienteDTOUpdated = atualizacaoClienteController.execute(1L, clienteDTO);
            assertThat(clienteDTOUpdated).isInstanceOf(ClienteDTO.class).isNotNull();
        }

    }

}
