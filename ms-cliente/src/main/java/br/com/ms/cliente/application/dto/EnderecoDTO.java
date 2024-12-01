package br.com.ms.cliente.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoDTO {

    private Long id;

    @NotNull(message = "o campo logradouro não pode ser nulo")
    @NotEmpty(message = "o campo logradouro não pode ser vazio")
    private String logradouro;

    @NotNull(message = "o campo bairro não pode ser nulo")
    @NotEmpty(message = "o campo bairro não pode ser vazio")
    private String bairro;

    @NotNull(message = "o campo cidade não pode ser nulo")
    @NotEmpty(message = "o campo cidade não pode ser vazio")
    private String cidade;

    @NotNull(message = "o campo uf não pode ser nulo")
    @NotEmpty(message = "o campo uf não pode ser vazio")
    private String uf;

    @NotNull(message = "o campo cep não pode ser nulo")
    @Positive(message = "o campo cep nao pode ser zero ou menor que zero")
    @Digits(integer = 8, fraction = 0, message = "o campo cep não pode ter casas decimais e não pode ter mais que 8 dígitos")
    private Long cep;

}
