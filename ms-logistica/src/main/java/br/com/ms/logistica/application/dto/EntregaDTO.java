package br.com.ms.logistica.application.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntregaDTO {

    private Long id;
    private String status;

    @NotNull(message = "o campo identificador do cliente não pode ser nulo")
    @Positive(message = "o campo identificador do cliente nao pode ser zero ou menor que zero")
    @Digits(integer = 19, fraction = 0, message = "o campo identificador do cliente não pode ter casas decimais e não pode ter mais que 19 dígitos")
    private Long clienteId;

    @NotNull(message = "o campo identificador do pedido não pode ser nulo")
    @Positive(message = "o campo identificador do pedido nao pode ser zero ou menor que zero")
    @Digits(integer = 19, fraction = 0, message = "o campo identificador do pedido não pode ter casas decimais e não pode ter mais que 19 dígitos")
    private Long pedidoId;


}
