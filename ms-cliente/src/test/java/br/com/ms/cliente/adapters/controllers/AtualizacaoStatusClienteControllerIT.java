package br.com.ms.cliente.adapters.controllers;

import br.com.ms.cliente.application.dto.ClienteDTO;
import br.com.ms.cliente.application.dto.EnderecoDTO;
import br.com.ms.cliente.application.usecases.AtualizaCliente;
import br.com.ms.cliente.application.usecases.BuscaCliente;
import br.com.ms.cliente.domain.entities.ClienteEntity;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
@AutoConfigureTestDatabase
public class AtualizacaoStatusClienteControllerIT {

    @Autowired
    private AtualizacaoStatusClienteController atualizacaoStatusClienteController;

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
    public class Validacao {

        @Test
        @DisplayName("Deve lançar exceção para cliente inexistente")
        public void deveLancarExcecaoParaClienteInexistente() {
            assertThatThrownBy(() -> atualizacaoStatusClienteController.execute(0L, StatusCliente.ATIVO))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("cliente não encontrado");
        }

    }

    @Nested
    public class AtualizandoStatus {

        @Test
        @DisplayName("Deve atualizar status do cliente")
        public void deveAtualizarStatusDoCliente() {
            atualizacaoStatusClienteController.execute(clienteDTOSetup.getId(), StatusCliente.INATIVO);
        }

    }

}
