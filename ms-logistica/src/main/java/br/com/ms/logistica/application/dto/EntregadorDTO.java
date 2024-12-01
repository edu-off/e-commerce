package br.com.ms.logistica.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntregadorDTO {

    private Long id;

    @NotNull(message = "o campo status não pode ser nulo")
    @NotEmpty(message = "o campo status não pode ser vazio")
    private String status;

    @NotNull(message = "o campo nome não pode ser nulo")
    @NotEmpty(message = "o campo nome não pode ser vazio")
    private String nome;

    @NotNull(message = "o campo email não pode ser nulo")
    @NotEmpty(message = "o campo email não pode ser vazio")
    @Email(message = "o campo email deve ser um e-mail válido")
    private String email;

}
