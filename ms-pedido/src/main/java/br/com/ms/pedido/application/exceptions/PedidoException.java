package br.com.ms.pedido.application.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PedidoException extends RuntimeException {

    private String message;

}
