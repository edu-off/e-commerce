package br.com.ms.cliente.domain.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class StatusClienteTest {

    @Nested
    public class Validacao {

        @Test
        @DisplayName("deve lançar exceção para string inválidas")
        public void deveLancarExcecaoParaStringInvalidas() {
            assertThatThrownBy(() -> StatusCliente.get("TESTE"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("status inválido");
        }

        @Test
        @DisplayName("deve retornar enum corretamente")
        public void deveRetornarEnumCorretamente() {
            assertThat(StatusCliente.get("ATIVO")).isEqualTo(StatusCliente.ATIVO);
            assertThat(StatusCliente.get("INATIVO")).isEqualTo(StatusCliente.INATIVO);
        }

    }

}
