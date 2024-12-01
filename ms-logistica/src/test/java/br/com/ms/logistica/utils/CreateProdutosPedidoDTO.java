package br.com.ms.logistica.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProdutosPedidoDTO {

    private Long id;
    private Integer quantidade;
    private Double preco;

}