package br.com.ms.logistica.application.usecases;

import br.com.ms.logistica.application.dto.ClienteDTO;
import br.com.ms.logistica.application.gateways.ClienteGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@Service
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
        @DisplayName("deve lancar excecao para cliente não encontrado")
        public void deveLancarExcecaoParaClienteNaoEncontrado() {
            when(clienteGateway.buscaPorId(1L)).thenReturn(null);
            assertThatThrownBy(() -> buscaCliente.run(1L))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("cliente não encontrado");
        }

        @Test
        @DisplayName("deve buscar cliente corretamente")
        public void deveBuscarClienteCorretamente() {
            ClienteDTO clienteDTO = new ClienteDTO(1L, "nome", "ATIVO", "teste@teste.com.br", 1, 1L, null);
            when(clienteGateway.buscaPorId(1L)).thenReturn(clienteDTO);
            buscaCliente.run(1L);
            verify(clienteGateway, times(1)).buscaPorId(1L);
        }

    }

}
