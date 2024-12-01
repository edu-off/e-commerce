package br.com.ms.produto.domain.utils;

import java.util.Objects;

public class Validator {

    public static boolean isValidString(String value) {
        return (isValidObject(value) && !value.isBlank());
    }

    public static boolean isValidNumber(Object value) {
        return (isValidObject(value) && value instanceof Number);
    }

    public static boolean isValidObject(Object object) {
        return Objects.nonNull(object);
    }

    public static boolean isValidPreco(Double preco) {
        if (!isValidNumber(preco))
            return false;
        return preco > 0;
    }

    public static boolean isValidQuantidade(Integer quantidade) {
        if (!isValidNumber(quantidade))
            return false;
        return quantidade >= 0;
    }

}
