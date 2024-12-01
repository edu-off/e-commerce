package br.com.ms.pedido.utils;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProdutoDTO {

    private Long id;
    private String nome;
    private String descricao;
    private Double preco;
    private Integer quantidade;
    private String categoria;

}
