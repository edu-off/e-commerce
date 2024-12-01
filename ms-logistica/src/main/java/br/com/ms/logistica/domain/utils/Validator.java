package br.com.ms.logistica.domain.utils;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class Validator {

    private static final Pattern VALID_EMAIL_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
            Pattern.CASE_INSENSITIVE);

    public static boolean isValidEmail(String email) {
        if (!isValidString(email))
            return false;
        return VALID_EMAIL_REGEX.matcher(email).matches();
    }

    public static boolean isValidString(String value) {
        return (isValidObject(value) && !value.isBlank());
    }

    public static boolean isValidNumber(Object value) {
        return (isValidObject(value) && value instanceof Number);
    }

    public static boolean isValidObject(Object object) {
        return Objects.nonNull(object);
    }

}
