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
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
@AutoConfigureTestDatabase
public class CadastroClienteControllerIT {

    @Autowired
    private CadastroClienteController cadastroClienteController;

    @Nested
    public class Validacoes {

        @Test
        @DisplayName("Deve lançar exceção para cliente inválido")
        public void deveLancarExcecaoParaClienteInvalido() {
            ClienteDTO clienteDTO = new ClienteDTO(null, "", "ATIVO", "teste@teste.com.br", 1, 1L, null);
            assertThatThrownBy(() -> cadastroClienteController.execute(clienteDTO))
                    .isInstanceOf(ClienteException.class)
                    .hasMessage("erro ao validar cliente: nome inválido");
        }

        @Test
        @DisplayName("Deve lançar exceção para endereço inválido")
        public void deveLancarExcecaoParaEndereçoInvalido() {
            EnderecoDTO enderecoDTO = new EnderecoDTO(null, "", "bairro", "cidade", "SP", 1L);
            ClienteDTO clienteDTO = new ClienteDTO(null, "nome", "ATIVO", "teste@teste.com.br", 1, 1L, enderecoDTO);
            assertThatThrownBy(() -> cadastroClienteController.execute(clienteDTO))
                    .isInstanceOf(EnderecoException.class)
                    .hasMessage("erro ao validar endereço: logradouro inválido");
        }

    }

    @Nested
    public class SalvandoCliente {

        @Test
        @DisplayName("Deve salvar Cliente corretamente")
        public void deveSalvarClienteCorretamente() {
            EnderecoDTO enderecoDTO = new EnderecoDTO(null, "logradouro", "bairro", "cidade", "SP", 1L);
            ClienteDTO clienteDTO = new ClienteDTO(null, "nome", "ATIVO", "teste@teste.com.br", 1, 1L, enderecoDTO);
            ClienteDTO clienteDTOCreated = cadastroClienteController.execute(clienteDTO);
            assertThat(clienteDTOCreated).isInstanceOf(ClienteDTO.class).isNotNull();
            assertThat(clienteDTOCreated.getId()).isNotNull().isNotZero();
        }

    }

}
