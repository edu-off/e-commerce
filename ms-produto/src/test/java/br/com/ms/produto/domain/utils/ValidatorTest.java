package br.com.ms.produto.domain.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static br.com.ms.produto.domain.utils.Validator.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ValidatorTest {

    @Nested
    public class ValidacaoStrings {

        @Test
        @DisplayName("Deve validar strings validas")
        public void deveValidarStringsValidas() {
            assertThat(isValidString("1")).isTrue();
            assertThat(isValidString("test")).isTrue();
        }

        @Test
        @DisplayName("Deve invalidar strings invalidas")
        public void deveInvalidarStringsInvalidas() {
            assertThat(isValidString(null)).isFalse();
            assertThat(isValidString("")).isFalse();
            assertThat(isValidString("    ")).isFalse();
        }

    }

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
    public class ValidacaoPreco {

        @Test
        @DisplayName("Deve validar precos validos")
        public void deveValidarPrecosValidos() {
            assertThat(isValidPreco(0.1)).isTrue();
            assertThat(isValidPreco(1.0)).isTrue();
        }

        @Test
        @DisplayName("Deve validar precos invalidos")
        public void deveValidarPrecosInvalidos() {
            assertThat(isValidPreco(null)).isFalse();
            assertThat(isValidPreco(0.0)).isFalse();
        }

    }

    @Nested
    public class ValidacaoQuantidade {

        @Test
        @DisplayName("Deve validar quantidades validas")
        public void deveValidarQuantidadeValidas() {
            assertThat(isValidQuantidade(0)).isTrue();
            assertThat(isValidQuantidade(1)).isTrue();
            assertThat(isValidQuantidade(2)).isTrue();
        }

        @Test
        @DisplayName("Deve validar precos invalidos")
        public void deveValidarPrecosInvalidos() {
            assertThat(isValidQuantidade(-1)).isFalse();
            assertThat(isValidQuantidade(-2)).isFalse();
        }

    }

}
