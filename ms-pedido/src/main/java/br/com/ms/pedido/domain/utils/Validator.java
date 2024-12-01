package br.com.ms.pedido.domain.utils;

import java.util.Objects;

public class Validator {

    public static boolean isValidNumber(Object value) {
        return (isValidObject(value) && value instanceof Number);
    }

    public static boolean isValidObject(Object object) {
        return Objects.nonNull(object);
    }

    public static boolean isValidProduto(Long id, Integer quantidade) {
        return isValidNumber(id) && id > 0 && isValidNumber(quantidade) && quantidade > 0;
    }

}
