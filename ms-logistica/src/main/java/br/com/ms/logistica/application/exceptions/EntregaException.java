package br.com.ms.logistica.application.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EntregaException extends RuntimeException {

    private String message;

}
