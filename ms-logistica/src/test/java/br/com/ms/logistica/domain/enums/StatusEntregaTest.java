package br.com.ms.logistica.domain.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class StatusEntregaTest {

    @Nested
    public class Validacao {

        @Test
        @DisplayName("deve lançar exceção para string inválidas")
        public void deveLancarExcecaoParaStringInvalidas() {
            assertThatThrownBy(() -> StatusEntrega.get("TESTE"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("status inválido");
        }

        @Test
        @DisplayName("deve retornar enum corretamente")
        public void deveRetornarEnumCorretamente() {
            assertThat(StatusEntrega.get("PENDENTE")).isEqualTo(StatusEntrega.PENDENTE);
            assertThat(StatusEntrega.get("EM_TRANSITO")).isEqualTo(StatusEntrega.EM_TRANSITO);
            assertThat(StatusEntrega.get("CANCELADA")).isEqualTo(StatusEntrega.CANCELADA);
            assertThat(StatusEntrega.get("CONCLUIDA")).isEqualTo(StatusEntrega.CONCLUIDA);
        }

    }

}
