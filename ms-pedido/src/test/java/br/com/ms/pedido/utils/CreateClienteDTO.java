package br.com.ms.pedido.utils;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateClienteDTO {

    private Long id;
    private String nome;
    private String status;
    private String email;
    private Integer ddd;
    private Long telefone;
    private CreateEnderecoDTO endereco;
}
