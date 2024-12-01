package br.com.ms.logistica.application.usecases;

import br.com.ms.logistica.application.dto.EntregadorDTO;
import br.com.ms.logistica.application.exceptions.EntregadorException;
import br.com.ms.logistica.domain.entities.EntregadorEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ValidaEntregadorTest {

    private final ValidaEntregador validaEntregador = new ValidaEntregador();

    @Nested
    public class ValidandoEntregador {

        @Test
        @DisplayName("Deve validar entregador corretamente")
        public void deveValidarEntregadorCorretamente() {
            EntregadorDTO entregadorDTO = new EntregadorDTO(null, "DISPONIVEL", "nome", "teste@teste.com.br");
            EntregadorEntity entregadorEntity = validaEntregador.run(entregadorDTO);
            assertThat(entregadorEntity).isInstanceOf(EntregadorEntity.class).isNotNull();
        }

        @Test
        @DisplayName("Deve lançar exceção para entregador inválido")
        public void deveLancarExcecaoParaEntregadorInvalido() {
            EntregadorDTO entregadorDTO = new EntregadorDTO(null, "", "nome", "teste@teste.com.br");
            assertThatThrownBy(() -> validaEntregador.run(entregadorDTO))
                    .isInstanceOf(EntregadorException.class)
                    .hasMessage("erro ao validar entregador: status inválido");
        }

    }

}
