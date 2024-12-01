package br.com.ms.logistica.application.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {

    private Long id;
    private String nome;
    private String status;
    private String email;
    private Integer ddd;
    private Long telefone;
    private EnderecoDTO endereco;

}
