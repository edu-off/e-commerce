package br.com.ms.logistica.domain.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static br.com.ms.logistica.domain.utils.Validator.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ValidatorTest {

    @Nested
    public class ValidacaoEmail {

        @Test
        @DisplayName("Deve validar e-mails inválidos")
        public void deveValidarEmailsInvalidos() {
            assertThat(isValidEmail(null)).isFalse();
            assertThat(isValidEmail("")).isFalse();
        }

        @Test
        @DisplayName("Deve validar e-mails com estrutura correta")
        public void deveValidarEmailsComEstruturaCorreta() {
            assertThat(isValidEmail("test@test.com")).isTrue();
            assertThat(isValidEmail("test@test.com.br")).isTrue();
        }

        @Test
        @DisplayName("Deve invalidar e-mails com estrutura incorreta")
        public void deveInvalidarEmailsComEstruturaIncorreta() {
            assertThat(isValidEmail("test")).isFalse();
            assertThat(isValidEmail("test@")).isFalse();
            assertThat(isValidEmail("@test")).isFalse();
            assertThat(isValidEmail("@test.com")).isFalse();
            assertThat(isValidEmail("@test.com.br")).isFalse();
            assertThat(isValidEmail("test.com")).isFalse();
            assertThat(isValidEmail("@test.com.br")).isFalse();
            assertThat(isValidEmail("@")).isFalse();
            assertThat(isValidEmail("test@test")).isFalse();
        }

    }

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

}
