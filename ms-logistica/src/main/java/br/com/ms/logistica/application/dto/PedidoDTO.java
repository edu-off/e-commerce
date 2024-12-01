package br.com.ms.logistica.application.dto;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {

    private Long id;
    private Long clienteId;
    private String status;
    private List<Map<String, Object>> produtos;

}
