package br.com.ms.logistica.domain.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class StatusEntregadorTest {

    @Nested
    public class Validacao {

        @Test
        @DisplayName("deve lançar exceção para string inválidas")
        public void deveLancarExcecaoParaStringInvalidas() {
            assertThatThrownBy(() -> StatusEntregador.get("TESTE"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("status inválido");
        }

        @Test
        @DisplayName("deve retornar enum corretamente")
        public void deveRetornarEnumCorretamente() {
            assertThat(StatusEntregador.get("DISPONIVEL")).isEqualTo(StatusEntregador.DISPONIVEL);
            assertThat(StatusEntregador.get("EM_TRANSITO")).isEqualTo(StatusEntregador.EM_TRANSITO);
            assertThat(StatusEntregador.get("INATIVO")).isEqualTo(StatusEntregador.INATIVO);
        }

    }

}
