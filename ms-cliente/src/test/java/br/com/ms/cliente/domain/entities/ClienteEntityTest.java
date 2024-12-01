package br.com.ms.cliente.domain.entities;

import br.com.ms.cliente.domain.enums.StatusCliente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ClienteEntityTest {

    private final String nome = "nome";
    private final StatusCliente status = StatusCliente.ATIVO;
    private final String email = "teste@teste.com.br";
    private final Integer ddd = 1;
    private final Long telefone = 1L;

    @Nested
    public class ValidacaoConstrutor {

        @Test
        @DisplayName("Deve instanciar Cliente corretamente")
        public void deveInstanciarClienteCorretamente() {
            ClienteEntity cliente = new ClienteEntity(nome, status, email, ddd, telefone);
            assertThat(cliente).isInstanceOf(ClienteEntity.class);
            assertThat(cliente.getNome()).isEqualTo(nome);
            assertThat(cliente.getStatus()).isEqualTo(status);
            assertThat(cliente.getEmail()).isEqualTo(email);
            assertThat(cliente.getDdd()).isEqualTo(ddd);
            assertThat(cliente.getTelefone()).isEqualTo(telefone);
        }

    }

    @Nested
    public class ValidacaoNome {

        @Test
        @DisplayName("Deve lançar exceção para nome nulo")
        public void deveLancarExcecaoParaNomeNulo() {
            String nomeNulo = null;
            assertThatThrownBy(() -> new ClienteEntity(nomeNulo, status, email, ddd, telefone))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("nome inválido");
        }

        @Test
        @DisplayName("Deve lançar exceção para nome em branco")
        public void deveLancarExcecaoParaNOmeEmBranco() {
            String nomeEmBranco = "";
            assertThatThrownBy(() -> new ClienteEntity(nomeEmBranco, status, email, ddd, telefone))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("nome inválido");
        }

    }

    @Nested
    public class ValidacaoStatus {

        @Test
        @DisplayName("Deve lançar exceção para status nulo")
        public void deveLancarExcecaoParaStatusNulo() {
            StatusCliente statusNulo = null;
            assertThatThrownBy(() -> new ClienteEntity(nome, statusNulo, email, ddd, telefone))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("status inválido");
        }

    }

    @Nested
    public class ValidacaoEmail {

        @Test
        @DisplayName("Deve lançar exceção para e-mail nulo")
        public void deveLancarExcecaoParaEmailNulo() {
            String emailNulo = null;
            assertThatThrownBy(() -> new ClienteEntity(nome, status, emailNulo, ddd, telefone))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("e-mail inválido");
        }

        @Test
        @DisplayName("Deve lançar exceção para e-mail em branco")
        public void deveLancarExcecaoParaEmailEmBranco() {
            String emailEmBranco = "";
            assertThatThrownBy(() -> new ClienteEntity(nome, status, emailEmBranco, ddd, telefone))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("e-mail inválido");
        }

        @Test
        @DisplayName("Deve lançar exceção para e-mail com estrutura inválida")
        public void deveLancarExcecaoParaEmailComEstruturaInvalida() {
            String emailEstruturaInvalida = "teste@";
            assertThatThrownBy(() -> new ClienteEntity(nome, status, emailEstruturaInvalida, ddd, telefone))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("e-mail inválido");
        }

    }

    @Nested
    public class ValidacaoDdd {

        @Test
        @DisplayName("Deve instanciar cliente com ddd nulo")
        public void deveInstanciarClienteComDddNulo() {
            Integer dddNulo = null;
            ClienteEntity cliente = new ClienteEntity(nome, status, email, dddNulo, telefone);
            assertThat(cliente).isInstanceOf(ClienteEntity.class).isNotNull();
            assertThat(cliente.getDdd()).isNull();
        }

        @Test
        @DisplayName("Deve lançar exceção para ddd minimo que o permitido")
        public void deveLancarExcecaoParaDddMinimoQueOPermitido() {
            Integer dddMenorQueOMinimo = 0;
            assertThatThrownBy(() -> new ClienteEntity(nome, status, email, dddMenorQueOMinimo, telefone))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("ddd inválido");
        }

        @Test
        @DisplayName("Deve lançar exceção para ddd máximo que o permitido")
        public void deveLancarExcecaoParaDddMaximoQueOPermitido() {
            Integer dddMaiorQueOMaximo = 100;
            assertThatThrownBy(() -> new ClienteEntity(nome, status, email, dddMaiorQueOMaximo, telefone))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("ddd inválido");
        }

    }

    @Nested
    public class ValidacaoTelefone {

        @Test
        @DisplayName("Deve instanciar cliente com telefone nulo")
        public void deveInstanciarClienteComTelefoneNulo() {
            Long telefoneNulo = null;
            ClienteEntity cliente = new ClienteEntity(nome, status, email, ddd, telefoneNulo);
            assertThat(cliente).isInstanceOf(ClienteEntity.class).isNotNull();
            assertThat(cliente.getTelefone()).isNull();
        }

        @Test
        @DisplayName("Deve lançar exceção para telefone minimo que o permitido")
        public void deveLancarExcecaoParaTelefoneMinimoQueOPermitido() {
            Long telfoneMenorQueOMinimo = 0L;
            assertThatThrownBy(() -> new ClienteEntity(nome, status, email, ddd, telfoneMenorQueOMinimo))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("telefone inválido");
        }

        @Test
        @DisplayName("Deve lançar exceção para telefone máximo que o permitido")
        public void deveLancarExcecaoParaTelefoneMaximoQueOPermitido() {
            Long telefoneMaiorQueOMaximo = 1000000000L;
            assertThatThrownBy(() -> new ClienteEntity(nome, status, email, ddd, telefoneMaiorQueOMaximo))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("telefone inválido");
        }

    }

}
