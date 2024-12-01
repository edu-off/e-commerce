package br.com.ms.cliente.application.usecases;

import br.com.ms.cliente.application.dto.ClienteDTO;
import br.com.ms.cliente.application.exceptions.ClienteException;
import br.com.ms.cliente.domain.entities.ClienteEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ValidacaoClienteTest {

    private final ValidacaoCliente validacaoCliente = new ValidacaoCliente();

    @Nested
    public class ValidandoCliente {

        @Test
        @DisplayName("Deve validar cliente corretamente")
        public void deveValidarClienteCorretamente() {
            ClienteDTO clienteDTO = new ClienteDTO(1L, "nome", "ATIVO", "teste@teste.com.br", 1, 1L, null);
            ClienteEntity clienteEntity = validacaoCliente.execute(clienteDTO);
            assertThat(clienteEntity).isInstanceOf(ClienteEntity.class).isNotNull();
        }

        @Test
        @DisplayName("Deve lançar exceção para cliente inválido")
        public void deveLancarExcecaoParaClienteInvalido() {
            ClienteDTO clienteDTO = new ClienteDTO(1L, "", "ATIVO", "teste@teste.com.br", 1, 1L, null);
            assertThatThrownBy(() -> validacaoCliente.execute(clienteDTO))
                    .isInstanceOf(ClienteException.class)
                    .hasMessage("erro ao validar cliente: nome inválido");
        }

    }

}
