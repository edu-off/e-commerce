package br.com.ms.cliente.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {

    private Long id;

    @NotNull(message = "o campo nome não pode ser nulo")
    @NotEmpty(message = "o campo nome não pode ser vazio")
    private String nome;

    @NotNull(message = "o campo status não pode ser nulo")
    @NotEmpty(message = "o campo status não pode ser vazio")
    private String status;

    @NotNull(message = "o campo email não pode ser nulo")
    @NotEmpty(message = "o campo email não pode ser vazio")
    @Email(message = "o campo e-mail deve possuir a estrutura válida de um e-mail")
    private String email;

    @NotNull(message = "o campo nota não pode ser nulo")
    @Positive(message = "o campo nota nao pode ser zero ou menor que zero")
    @Digits(integer = 3, fraction = 0, message = "o campo nota não pode ter casas decimais e não pode ter mais que 3 dígitos")
    private Integer ddd;

    @NotNull(message = "o campo telefone não pode ser nulo")
    @Positive(message = "o campo telefone nao pode ser zero ou menor que zero")
    @Digits(integer = 9, fraction = 0, message = "o campo telefone não pode ter casas decimais e não pode ter mais que 9 dígitos")
    private Long telefone;

    @Valid
    @NotNull(message = "objeto endereco não pode ser nulo")
    private EnderecoDTO endereco;

}
