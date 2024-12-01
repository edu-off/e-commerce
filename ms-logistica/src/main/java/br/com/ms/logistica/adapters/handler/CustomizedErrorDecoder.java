package br.com.ms.logistica.adapters.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;

public class CustomizedErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        ExceptionResponse message;
        try (InputStream bodyIs = response.body().asInputStream()) {
            message = new ObjectMapper().readValue(bodyIs, ExceptionResponse.class);
        } catch (IOException e) {
            return new Exception(e.getMessage());
        }

        return switch (response.status()) {
            case 400 -> new IllegalArgumentException(message.getMessage());
            case 404 -> new NoSuchElementException(message.getMessage());
            default -> new Exception(message.getMessage());
        };
    }

}
