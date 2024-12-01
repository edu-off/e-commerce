package br.com.ms.pedido.utils;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateEnderecoDTO {

    private Long id;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String uf;
    private Long cep;
}
