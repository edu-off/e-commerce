package br.com.ms.cliente.domain.entities;

import br.com.ms.cliente.domain.enums.StatusCliente;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

import static br.com.ms.cliente.domain.utils.Validator.*;

@Getter
@Setter
@NoArgsConstructor
public class ClienteEntity {

    private Long id;
    private String nome;
    private StatusCliente status;
    private String email;
    private Integer ddd;
    private Long telefone;

    public ClienteEntity(String nome, StatusCliente status, String email, Integer ddd, Long telefone) {
        if (!isValidString(nome))
            throw new IllegalArgumentException("nome inválido");
        if (!isValidObject(status))
            throw new IllegalArgumentException("status inválido");
        if (!isValidEmail(email))
            throw new IllegalArgumentException("e-mail inválido");
        if (Objects.nonNull(ddd)) {
            if (!isValidDdd(ddd))
                throw new IllegalArgumentException("ddd inválido");
        }
        if (Objects.nonNull(telefone)) {
            if (!isValidTelefone(telefone))
                throw new IllegalArgumentException("telefone inválido");
        }
        this.nome = nome;
        this.status = status;
        this.ddd = ddd;
        this.telefone = telefone;
        this.email = email;
    }

}
