package br.com.ms.cliente.domain.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class EnderecoEntityTest {

    private final String logradouro = "logradouro válido";
    private final String bairro = "bairro válido";
    private final String cidade = "cidade válida";
    private final String uf = "SP";
    private final Long cep = 1L;
    private final ClienteEntity cliente = new ClienteEntity();

    @Nested
    public class ValidacaoConstrutor {

        @Test
        @DisplayName("Deve instanciar Endereco corretamente")
        public void deveInstanciarEnderecoCorretamente() {
            EnderecoEntity endereco = new EnderecoEntity(logradouro, bairro, cidade, uf, cep, cliente);
            assertThat(endereco).isInstanceOf(EnderecoEntity.class);
            assertThat(endereco.getLogradouro()).isEqualTo(logradouro);
            assertThat(endereco.getBairro()).isEqualTo(bairro);
            assertThat(endereco.getCidade()).isEqualTo(cidade);
            assertThat(endereco.getUf()).isEqualTo(uf);
            assertThat(endereco.getCep()).isEqualTo(cep);
        }

    }

    @Nested
    public class ValidacaoLogradouro {

        @Test
        @DisplayName("Deve lançar exceção para logradouro nulo")
        public void deveLancarExcecaoParaLogradouroNulo() {
            String logradouroNulo = null;
            assertThatThrownBy(() -> new EnderecoEntity(logradouroNulo, bairro, cidade, uf, cep, cliente))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("logradouro inválido");
        }

        @Test
        @DisplayName("Deve lançar exceção para logradouro em branco")
        public void deveLancarExcecaoParaLogradouroEmBranco() {
            String logradouroEmBranco = "";
            assertThatThrownBy(() -> new EnderecoEntity(logradouroEmBranco, bairro, cidade, uf, cep, cliente))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("logradouro inválido");
        }

    }

    @Nested
    public class ValidacaoBairro {

        @Test
        @DisplayName("Deve lançar exceção para bairro nulo")
        public void deveLancarExcecaoParaBairroNulo() {
            String bairroNulo = null;
            assertThatThrownBy(() -> new EnderecoEntity(logradouro, bairroNulo, cidade, uf, cep, cliente))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("bairro inválido");
        }

        @Test
        @DisplayName("Deve lançar exceção para bairro em branco")
        public void deveLancarExcecaoParaBairroEmBranco() {
            String bairroEmBranco = "";
            assertThatThrownBy(() -> new EnderecoEntity(logradouro, bairroEmBranco, cidade, uf, cep, cliente))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("bairro inválido");
        }

    }

    @Nested
    public class ValidacaoCidade {

        @Test
        @DisplayName("Deve lançar exceção para cidade nula")
        public void deveLancarExcecaoParaCidadeNula() {
            String cidadeNula = null;
            assertThatThrownBy(() -> new EnderecoEntity(logradouro, bairro, cidadeNula, uf, cep, cliente))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("cidade inválida");
        }

        @Test
        @DisplayName("Deve lançar exceção para cidade em branco")
        public void deveLancarExcecaoParaCidadeEmBranco() {
            String cidadeEmBranco = "";
            assertThatThrownBy(() -> new EnderecoEntity(logradouro, bairro, cidadeEmBranco, uf, cep, cliente))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("cidade inválida");
        }

    }

    @Nested
    public class ValidacaoUf {

        @Test
        @DisplayName("Deve lançar exceção para uf nula")
        public void deveLancarExcecaoParaUfNula() {
            String ufNula = null;
            assertThatThrownBy(() -> new EnderecoEntity(logradouro, bairro, cidade, ufNula, cep, cliente))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("uf inválida");
        }

        @Test
        @DisplayName("Deve lançar exceção para uf em branco")
        public void deveLancarExcecaoParaUfEmBranco() {
            String ufEmBranco = "";
            assertThatThrownBy(() -> new EnderecoEntity(logradouro, bairro, cidade, ufEmBranco, cep, cliente))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("uf inválida");
        }

        @Test
        @DisplayName("Deve lançar exceção para uf inválida")
        public void deveLancarExcecaoParaUfInvalida() {
            String ufInvalida = "XX";
            assertThatThrownBy(() -> new EnderecoEntity(logradouro, bairro, cidade, ufInvalida, cep, cliente))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("uf inválida");
        }

    }

    @Nested
    public class ValidacaoCep {

        @Test
        @DisplayName("Deve lançar exceção para cep nulo")
        public void deveLancarExcecaoParaCepNulo() {
            Long cepNulo = null;
            assertThatThrownBy(() -> new EnderecoEntity(logradouro, bairro, cidade, uf, cepNulo, cliente))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("cep inválido");
        }

        @Test
        @DisplayName("Deve lançar exceção para cep inferior ao minimo permitido")
        public void deveLancarExcecaoParaCepInferiorAoMinimoPermitido() {
            Long cepInferiorAoMinimoPermitido = 0L;
            assertThatThrownBy(() -> new EnderecoEntity(logradouro, bairro, cidade, uf, cepInferiorAoMinimoPermitido, cliente))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("cep inválido");
        }

        @Test
        @DisplayName("Deve lançar exceção para cep superior ao máximo permitido")
        public void deveLancarExcecaoParaCepSuperiorAoMaximoPermitido() {
            Long cepSuperiorAoMaximoPermitido = 100000000L;
            assertThatThrownBy(() -> new EnderecoEntity(logradouro, bairro, cidade, uf, cepSuperiorAoMaximoPermitido, cliente))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("cep inválido");
        }

    }


    @Nested
    public class ValidacaoCliente {

        @Test
        @DisplayName("Deve lançar exceção para cliente nulo")
        public void deveLancarExcecaoParaClienteNulo() {
            ClienteEntity clienteEntityNulo = null;
            assertThatThrownBy(() -> new EnderecoEntity(logradouro, bairro, cidade, uf, cep, clienteEntityNulo))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("cliente inválido");
        }

    }

}
