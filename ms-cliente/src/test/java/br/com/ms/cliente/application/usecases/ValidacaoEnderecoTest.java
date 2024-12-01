package br.com.ms.cliente.application.usecases;

import br.com.ms.cliente.application.dto.EnderecoDTO;
import br.com.ms.cliente.application.exceptions.EnderecoException;
import br.com.ms.cliente.domain.entities.ClienteEntity;
import br.com.ms.cliente.domain.entities.EnderecoEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ValidacaoEnderecoTest {

    private final ValidacaoEndereco validacaoEndereco = new ValidacaoEndereco();

    @Nested
    public class ValidandoEndereco {

        @Test
        @DisplayName("Deve validar endereço corretamente")
        public void deveValidarEnderecoCorretamente() {
            EnderecoDTO enderecoDTO = new EnderecoDTO(1L, "logradouro", "bairro", "cidade", "SP", 1L);
            EnderecoEntity enderecoEntity = validacaoEndereco.execute(enderecoDTO, new ClienteEntity());
            assertThat(enderecoEntity).isInstanceOf(EnderecoEntity.class).isNotNull();
        }

        @Test
        @DisplayName("Deve lançar exceção para cliente inválido")
        public void deveLancarExcecaoParaClienteInvalido() {
            EnderecoDTO enderecoDTO = new EnderecoDTO(1L, "", "bairro", "cidade", "SP", 1L);
            assertThatThrownBy(() -> validacaoEndereco.execute(enderecoDTO, new ClienteEntity()))
                    .isInstanceOf(EnderecoException.class)
                    .hasMessage("erro ao validar endereço: logradouro inválido");
        }

    }

}
