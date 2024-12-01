package br.com.ms.logistica.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusEntregador {

    DISPONIVEL("DISPONIVEL"),
    EM_TRANSITO("EM_TRANSITO"),
    INATIVO("INATIVO");

    private final String value;

    public static StatusEntregador get(String value) {
        for(StatusEntregador status : StatusEntregador.values()) {
            if(status.getValue().equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("status inválido");
    }

}
