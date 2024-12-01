package br.com.ms.logistica.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusEntrega {

    PENDENTE("PENDENTE"),
    EM_TRANSITO("EM_TRANSITO"),
    CANCELADA("CANCELADA"),
    CONCLUIDA("CONCLUIDA");

    private final String value;

    public static StatusEntrega get(String value) {
        for(StatusEntrega status : StatusEntrega.values()) {
            if(status.getValue().equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("status inválido");
    }

}
