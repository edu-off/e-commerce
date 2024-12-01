package br.com.ms.logistica.domain.entities;

import br.com.ms.logistica.domain.enums.StatusEntregador;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class EntregadorEntityTest {

    private final String nome = "nome válido";
    private final StatusEntregador status = StatusEntregador.DISPONIVEL;
    private final String email = "teste@teste.com.br";

    @Nested
    public class ValidacaoConstrutor {

        @Test
        @DisplayName("Deve instanciar Entregador corretamente")
        public void deveInstanciarEntregadorCorretamente() {
            EntregadorEntity entregador = new EntregadorEntity(status, nome, email);
            assertThat(entregador).isInstanceOf(EntregadorEntity.class).isNotNull();
            assertThat(entregador.getStatus()).isEqualTo(status);
            assertThat(entregador.getNome()).isEqualTo(nome);
            assertThat(entregador.getEmail()).isEqualTo(email);
        }

    }

    @Nested
    public class ValidacaoStatus {

        @Test
        @DisplayName("Deve lançar exceção para status nulo")
        public void deveLancarExcecaoParaStatusNulo() {
            StatusEntregador statusNulo = null;
            assertThatThrownBy(() -> new EntregadorEntity(statusNulo, nome, email))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("status do entregador inválido");
        }

    }

    @Nested
    public class ValidacaoNome {

        @Test
        @DisplayName("Deve lançar exceção para nome nulo")
        public void deveLancarExcecaoParaNomeNulo() {
            String nomeNulo = null;
            assertThatThrownBy(() -> new EntregadorEntity(status, nomeNulo, email))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("nome inválido");
        }

        @Test
        @DisplayName("Deve lançar exceção para nome em branco")
        public void deveLancarExcecaoParaNomeEmBranco() {
            String nomeEmBranco = null;
            assertThatThrownBy(() -> new EntregadorEntity(status, nomeEmBranco, email))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("nome inválido");
        }

    }

    @Nested
    public class ValidacaoEmail {

        @Test
        @DisplayName("Deve lançar exceção para e-mail nulo")
        public void deveLancarExcecaoParaEmailNulo() {
            String emailNulo = null;
            assertThatThrownBy(() -> new EntregadorEntity(status, nome, emailNulo))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("e-mail inválido");
        }

        @Test
        @DisplayName("Deve lançar exceção para e-mail em branco")
        public void deveLancarExcecaoParaEmailEmBranco() {
            String emailEmBranco = "";
            assertThatThrownBy(() -> new EntregadorEntity(status, nome, emailEmBranco))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("e-mail inválido");
        }

        @Test
        @DisplayName("Deve lançar exceção para e-mail com estrutura inválida")
        public void deveLancarExcecaoParaEmailComEstruturaInvalida() {
            String emailEstruturaInvalida = "teste@";
            assertThatThrownBy(() -> new EntregadorEntity(status, nome, emailEstruturaInvalida))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("e-mail inválido");
        }

    }


}
