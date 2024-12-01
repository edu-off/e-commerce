package br.com.ms.cliente.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static br.com.ms.cliente.domain.utils.Validator.*;

@Getter
@Setter
@NoArgsConstructor
public class EnderecoEntity {

    private Long id;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String uf;
    private Long cep;
    private ClienteEntity cliente;

    public EnderecoEntity(String logradouro, String bairro, String cidade, String uf, Long cep, ClienteEntity cliente) {
        if (!isValidString(logradouro))
            throw new IllegalArgumentException("logradouro inválido");
        if (!isValidString(bairro))
            throw new IllegalArgumentException("bairro inválido");
        if (!isValidString(cidade))
            throw new IllegalArgumentException("cidade inválida");
        if (!isValidString(uf) || (!isValidUf(uf)))
            throw new IllegalArgumentException("uf inválida");
        if (!isValidNumber(cep) || !isValidCep(cep))
            throw new IllegalArgumentException("cep inválido");
        if (!isValidObject(cliente))
            throw new IllegalArgumentException("cliente inválido");
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
        this.cep = cep;
        this.cliente = cliente;
    }

}