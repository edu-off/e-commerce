package br.com.ms.produto.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static br.com.ms.produto.domain.utils.Validator.*;

@Getter
@Setter
@NoArgsConstructor
public class ProdutoEntity {

    private Long id;
    private String nome;
    private String descricao;
    private Double preco;
    private Integer quantidade;
    private String categoria;

    public ProdutoEntity(String nome, String descricao, Double preco, Integer quantidade, String categoria) {
        if (!isValidString(nome))
            throw new IllegalArgumentException("nome inválido");
        if (!isValidString(descricao))
            throw new IllegalArgumentException("descrição inválida");
        if (!isValidPreco(preco))
            throw new IllegalArgumentException("preço inválido");
        if (!isValidQuantidade(quantidade))
            throw new IllegalArgumentException("quantidade inválida");
        if (!isValidString(categoria))
            throw new IllegalArgumentException("categoria inválida");

        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidade = quantidade;
        this.categoria = categoria;
    }

    public void adicionaQuantidade(Integer quantidade) {
        if (quantidade >= 10000000)
            throw new IllegalArgumentException("quantidade é superior ao limite suportado");

        if (this.quantidade + quantidade >= 10000000)
            throw new IllegalArgumentException("estoque não pode estourar 9.999.999 itens");

        this.quantidade = this.quantidade + quantidade;
    }

    public void subtraiQuantidade(Integer quantidade) {
        if (this.quantidade < quantidade)
            throw new IllegalArgumentException("quantidade a ser subtraida é superior a quantidade existente");

        this.quantidade = this.quantidade - quantidade;
    }

}
