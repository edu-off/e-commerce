package br.com.ms.cliente.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public enum StatusCliente {

    ATIVO("ATIVO"),
    INATIVO("INATIVO");

    private final String value;

    public static StatusCliente get(String value) {
        for(StatusCliente status : StatusCliente.values()) {
            if(status.getValue().equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("status inválido");
    }

}
