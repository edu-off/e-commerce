package br.com.ms.produto.application.dto;

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
public class ProdutoDTO {

    private Long id;

    @NotNull(message = "o campo nome não pode ser nulo")
    @NotEmpty(message = "o campo nome não pode ser vazio")
    private String nome;

    @NotNull(message = "o campo descrição não pode ser nulo")
    @NotEmpty(message = "o campo descrição não pode ser vazio")
    private String descricao;

    @NotNull(message = "o campo preço não pode ser nulo")
    @Positive(message = "o campo preço nao pode ser zero ou menor que zero")
    @Digits(integer = 7, fraction = 2, message = "o campo preço não pode ter mais de 2 casas decimais e não pode ter mais que 7 dígitos")
    private Double preco;

    @NotNull(message = "o campo quantidade não pode ser nulo")
    @Positive(message = "o campo quantidade nao pode ser zero ou menor que zero")
    @Digits(integer = 7, fraction = 0, message = "o campo quantidade não pode ter casas decimais e não pode ter mais que 7 dígitos")
    private Integer quantidade;

    @NotNull(message = "o campo categoria não pode ser nulo")
    @NotEmpty(message = "o campo categoria não pode ser vazio")
    private String categoria;

}
