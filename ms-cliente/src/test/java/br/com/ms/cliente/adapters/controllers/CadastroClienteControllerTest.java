package br.com.ms.cliente.adapters.controllers;

import br.com.ms.cliente.adapters.presenters.ClientePresenter;
import br.com.ms.cliente.application.dto.ClienteDTO;
import br.com.ms.cliente.application.dto.EnderecoDTO;
import br.com.ms.cliente.application.exceptions.ClienteException;
import br.com.ms.cliente.application.exceptions.EnderecoException;
import br.com.ms.cliente.application.usecases.SalvaCliente;
import br.com.ms.cliente.application.usecases.SalvaEndereco;
import br.com.ms.cliente.application.usecases.ValidacaoCliente;
import br.com.ms.cliente.application.usecases.ValidacaoEndereco;
import br.com.ms.cliente.domain.entities.ClienteEntity;
import br.com.ms.cliente.domain.entities.EnderecoEntity;
import br.com.ms.cliente.domain.enums.StatusCliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CadastroClienteControllerTest {

    @Mock
    private ValidacaoCliente validacaoCliente;

    @Mock
    private ValidacaoEndereco validacaoEndereco;

    @Mock
    private ClientePresenter presenter;

    @Mock
    private SalvaCliente salvaCliente;

    @Mock
    private SalvaEndereco salvaEndereco;

    @InjectMocks
    private CadastroClienteController cadastroClienteController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class Validacoes {

        @Test
        @DisplayName("Deve lançar exceção para cliente inválido")
        public void deveLancarExcecaoParaClienteInvalido() {
            ClienteDTO clienteDTO = new ClienteDTO(null, "nome", "ATIVO", "teste@teste.com.br", 1, 1L, null);
            Mockito.when(validacaoCliente.execute(clienteDTO)).thenThrow(new ClienteException("cliente inválido"));
            assertThatThrownBy(() -> cadastroClienteController.execute(clienteDTO))
                    .isInstanceOf(ClienteException.class)
                    .hasMessage("cliente inválido");
        }

        @Test
        @DisplayName("Deve lançar exceção para endereço inválido")
        public void deveLancarExcecaoParaEnderecoInvalido() {
            EnderecoDTO enderecoDTO = new EnderecoDTO(null, "logradouro", "bairro", "cidade", "SP", 1L);
            ClienteDTO clienteDTO = new ClienteDTO(null, "nome", "ATIVO", "teste@teste.com.br", 1, 1L, enderecoDTO);
            ClienteEntity clienteEntity = new ClienteEntity("nome", StatusCliente.ATIVO, "teste@teste.com.br", 1, 1L);
            Mockito.when(validacaoCliente.execute(clienteDTO)).thenReturn(clienteEntity);
            Mockito.when(validacaoEndereco.execute(enderecoDTO, clienteEntity)).thenThrow(new EnderecoException("endereço inválido"));
            assertThatThrownBy(() -> cadastroClienteController.execute(clienteDTO))
                    .isInstanceOf(EnderecoException.class)
                    .hasMessage("endereço inválido");
        }

    }

    @Nested
    public class SalvandoCliente {

        @Test
        @DisplayName("Deve salvar Cliente corretamente")
        public void deveSalvarClienteCorretamente() {
            EnderecoDTO enderecoDTO = new EnderecoDTO(null, "logradouro", "bairro", "cidade", "SP", 1L);
            ClienteDTO clienteDTO = new ClienteDTO(null, "nome", "ATIVO", "teste@teste.com.br", 1, 1L, enderecoDTO);
            ClienteEntity clienteEntity = new ClienteEntity("nome", StatusCliente.ATIVO, "teste@teste.com.br", 1, 1L);
            EnderecoEntity enderecoEntity = new EnderecoEntity("logradouro", "bairro", "cidade", "SP", 1L, clienteEntity);
            Mockito.when(validacaoCliente.execute(clienteDTO)).thenReturn(clienteEntity);
            Mockito.when(validacaoEndereco.execute(enderecoDTO, clienteEntity)).thenReturn(enderecoEntity);
            Mockito.when(salvaCliente.execute(clienteEntity)).thenReturn(clienteEntity);
            Mockito.when(salvaEndereco.execute(enderecoEntity)).thenReturn(enderecoEntity);
            Mockito.when(presenter.transform(clienteEntity, enderecoEntity)).thenReturn(clienteDTO);
            ClienteDTO clienteDTOCreated = cadastroClienteController.execute(clienteDTO);
            assertThat(clienteDTOCreated).isInstanceOf(ClienteDTO.class).isNotNull();
        }

    }

}
