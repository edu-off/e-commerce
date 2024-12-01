package br.com.ms.pedido.domain.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static br.com.ms.pedido.domain.utils.Validator.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ValidatorTest {

    @Nested
    public class ValidacaoNumeros {

        @Test
        @DisplayName("Deve validar objetos de numeros")
        public void deveValidarObjetosDeNumeros() {
            assertThat(isValidNumber(Short.valueOf("1"))).isTrue();
            assertThat(isValidNumber(Integer.valueOf("1"))).isTrue();
            assertThat(isValidNumber(Long.valueOf("1"))).isTrue();
            assertThat(isValidNumber(BigDecimal.valueOf(1L))).isTrue();
        }

        @Test
        @DisplayName("Deve invalidar objetos que não de numeros")
        public void deveInvalidarObjetosQueNaoSaoDeNumeros() {
            assertThat(isValidNumber(null)).isFalse();
            assertThat(isValidNumber("")).isFalse();
            assertThat(isValidNumber("    ")).isFalse();
        }

    }

    @Nested
    public class ValidacaoObjetos {

        @Test
        @DisplayName("Deve validar objetos nao nulos")
        public void deveValidarObjetosNaoNulos() {
            assertThat(isValidObject("1")).isTrue();
            assertThat(isValidObject("")).isTrue();
            assertThat(isValidObject(" ")).isTrue();
        }

        @Test
        @DisplayName("Deve invalidar objetos nulos")
        public void deveInvalidarObjetosQueNaoSaoDeNumeros() {
            assertThat(isValidObject(null)).isFalse();
        }

    }

    @Nested
    public class ValidacaoProduto {

        @Test
        @DisplayName("Deve validar id de produto inválido")
        public void deveValidarIdDeProdutoInvalido() {
            assertThat(isValidProduto(null, 1)).isFalse();
            assertThat(isValidProduto(0L, 1)).isFalse();
        }

        @Test
        @DisplayName("Deve validar quantidade de produto inválida")
        public void deveValidarQuantidadeDeProdutoInvalida() {
            assertThat(isValidProduto(1L, null)).isFalse();
            assertThat(isValidProduto(1L, 0)).isFalse();
        }

        @Test
        @DisplayName("Deve validar id e quantidade de produto válidas")
        public void deveValidarIdEQuantidadeDeProdutovalidas() {
            assertThat(isValidProduto(1L, 1)).isTrue();
            assertThat(isValidProduto(2L, 2)).isTrue();
        }

    }

}
