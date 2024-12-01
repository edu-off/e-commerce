package br.com.ms.pedido.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {

    @NotNull(message = "o campo produto id não pode ser nulo")
    @Positive(message = "o campo produto id nao pode ser zero ou menor que zero")
    @Digits(integer = 19, fraction = 0, message = "o campo produto id não pode ter casas decimais e não pode ter mais que 19 dígitos")
    private Long id;

    @NotNull(message = "o campo quantidade não pode ser nulo")
    @Positive(message = "o campo quantidade nao pode ser zero ou menor que zero")
    @Digits(integer = 7, fraction = 0, message = "o campo quantidade não pode ter casas decimais e não pode ter mais que 7 dígitos")
    private Integer quantidade;

    @NotNull(message = "o campo preço não pode ser nulo")
    @Positive(message = "o campo preço nao pode ser zero ou menor que zero")
    @Digits(integer = 7, fraction = 2, message = "o campo preço não pode ter mais de 2 casas decimais e não pode ter mais que 7 dígitos")
    private Double preco;

}
