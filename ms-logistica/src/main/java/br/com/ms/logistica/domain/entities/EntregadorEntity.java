package br.com.ms.logistica.domain.entities;

import br.com.ms.logistica.domain.enums.StatusEntregador;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static br.com.ms.logistica.domain.utils.Validator.*;

@Getter
@Setter
@NoArgsConstructor
public class EntregadorEntity {

    private Long id;
    private StatusEntregador status;
    private String nome;
    private String email;

    public EntregadorEntity(StatusEntregador status, String nome, String email) {
        if (!isValidObject(status))
            throw new IllegalArgumentException("status do entregador inválido");
        if (!isValidString(nome))
            throw new IllegalArgumentException("nome inválido");
        if (!isValidEmail(email))
            throw new IllegalArgumentException("e-mail inválido");

        this.status = status;
        this.nome = nome;
        this.email = email;
    }

}
