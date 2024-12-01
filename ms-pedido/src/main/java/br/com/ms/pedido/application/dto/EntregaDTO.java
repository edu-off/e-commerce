package br.com.ms.pedido.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntregaDTO {

    private Long id;
    private String status;
    private Long clienteId;
    private Long pedidoId;

}
