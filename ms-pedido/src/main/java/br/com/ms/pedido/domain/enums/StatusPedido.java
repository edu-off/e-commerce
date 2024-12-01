package br.com.ms.pedido.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusPedido {

    EM_ABERTO("EM_ABERTO"),
    CONFIRMADO("CONFIRMADO"),
    CONCLUIDO("CONCLUIDO"),
    CANCELADO("CANCELADO");

    private final String value;

    public static StatusPedido get(String value) {
        for(StatusPedido status : StatusPedido.values()) {
            if(status.getValue().equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("status inválido");
    }

}
