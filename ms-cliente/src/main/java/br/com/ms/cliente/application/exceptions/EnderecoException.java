package br.com.ms.cliente.application.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EnderecoException extends RuntimeException {

    private String message;

}
