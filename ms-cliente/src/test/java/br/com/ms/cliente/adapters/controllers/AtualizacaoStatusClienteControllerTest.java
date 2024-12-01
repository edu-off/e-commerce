package br.com.ms.cliente.adapters.controllers;

import br.com.ms.cliente.application.dto.ClienteDTO;
import br.com.ms.cliente.application.usecases.AtualizaCliente;
import br.com.ms.cliente.application.usecases.BuscaCliente;
import br.com.ms.cliente.domain.entities.ClienteEntity;
import br.com.ms.cliente.domain.enums.StatusCliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AtualizacaoStatusClienteControllerTest {

    @Mock
    private BuscaCliente buscaCliente;

    @Mock
    private AtualizaCliente atualizaCliente;

    @InjectMocks
    private AtualizacaoStatusClienteController atualizacaoStatusClienteController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class Validacao {

        @Test
        @DisplayName("Deve lançar exceção para cliente inexistente")
        public void deveLancarExcecaoParaClienteInexistente() {
            ClienteDTO clienteDTO = new ClienteDTO(1L, "nome", "ATIVO", "teste@teste,com.br", 1, 1L, null);
            Mockito.when(buscaCliente.execute(1L)).thenReturn(null);
            assertThatThrownBy(() -> atualizacaoStatusClienteController.execute(1L, StatusCliente.ATIVO))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("cliente não encontrado");
        }

    }

    @Nested
    public class AtualizandoStatus {

        @Test
        @DisplayName("Deve atualizar status do cliente")
        public void deveAtualizarStatusDoCliente() {
            ClienteDTO clienteDTO = new ClienteDTO(1L, "nome", "ATIVO", "teste@teste,com.br", 1, 1L, null);
            ClienteEntity clienteEntity = new ClienteEntity("nome", StatusCliente.ATIVO, "teste@teste.com.br", 1, 1L);
            Mockito.when(buscaCliente.execute(1L)).thenReturn(clienteEntity);
            Mockito.when(atualizaCliente.execute(1L, clienteEntity)).thenReturn(clienteEntity);
            atualizacaoStatusClienteController.execute(1L, StatusCliente.ATIVO);
            Mockito.verify(buscaCliente, Mockito.times(1)).execute(1L);
            Mockito.verify(atualizaCliente, Mockito.times(1)).execute(1L, clienteEntity);
        }

    }

}
