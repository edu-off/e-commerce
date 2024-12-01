package br.com.ms.pedido.application.usecases;

import br.com.ms.pedido.application.dto.ClienteDTO;
import br.com.ms.pedido.application.gateways.ClienteGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class BuscaClienteTest {

    @Mock
    private ClienteGateway clienteGateway;

    @InjectMocks
    private BuscaCliente buscaCliente;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class BuscandoCliente {

        @Test
        @DisplayName("Deve lançar exceção para cliente não encontrado")
        public void deveLancarExcecaoParaClienteNaoEncontrado() {
            ClienteDTO clienteDTO = new ClienteDTO(1L, "nome", "ATIVO", "teste@teste.com.br", 1, 1L);
            when(clienteGateway.buscaPorId(1L)).thenReturn(null);
            assertThatThrownBy(() -> buscaCliente.run(1L))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("cliente não encontrado");
        }

        @Test
        @DisplayName("Deve buscar pedido corretamente")
        public void deveBuscarPedidoCorretamente() {
            ClienteDTO clienteDTO = new ClienteDTO(1L, "nome", "ATIVO", "teste@teste.com.br", 1, 1L);
            when(clienteGateway.buscaPorId(1L)).thenReturn(clienteDTO);
            buscaCliente.run(1L);
            verify(clienteGateway, times(1)).buscaPorId(1L);
        }

    }

}
