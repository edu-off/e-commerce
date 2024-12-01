package br.com.ms.pedido.application.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {

    private Long id;

    @NotNull(message = "o campo cliente id não pode ser nulo")
    @Positive(message = "o campo cliente id nao pode ser zero ou menor que zero")
    @Digits(integer = 19, fraction = 0, message = "o campo cliente id não pode ter casas decimais e não pode ter mais que 19 dígitos")
    private Long clienteId;

    @NotNull(message = "o campo status não pode ser nulo")
    @NotEmpty(message = "o campo status não pode ser vazio")
    private String status;

    @NotNull(message = "a lista de produtos não pode ser nula")
    @NotEmpty(message = "a lista de produtos não estar vazia")
    private List<ProdutoDTO> produtos;

}
