package br.com.ms.logistica.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePedidoDTO {

    private Long id;
    private Long clienteId;
    private String status;
    private List<CreateProdutosPedidoDTO> produtos;


}