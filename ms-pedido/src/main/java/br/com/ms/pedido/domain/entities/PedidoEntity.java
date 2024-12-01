package br.com.ms.pedido.domain.entities;

import br.com.ms.pedido.domain.enums.StatusPedido;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

import static br.com.ms.pedido.domain.utils.Validator.*;

@Getter
@Setter
@NoArgsConstructor
public class PedidoEntity {

    private Long id;
    private Long clienteId;
    private StatusPedido status;
    private Double preco;
    private Map<Long, Integer> produtos;
    private LocalDateTime dataAbertura;
    private LocalDateTime dataConfirmacao;
    private LocalDateTime dataCancelamento;
    private LocalDateTime dataConclusao;

    public PedidoEntity(Long clienteId, StatusPedido status, Map<Long, Integer> produtos) {
        if (!isValidNumber(clienteId) || clienteId == 0L)
            throw new IllegalArgumentException("identificador do cliente inválido");
        if (!isValidObject(status))
            throw new IllegalArgumentException("status do pedido inválido");
        if (!isValidObject(produtos))
            throw new IllegalArgumentException("lista de produtos inválida");
        if (produtos.isEmpty())
            throw new IllegalArgumentException("pedido sem produtos");
        produtos.forEach((id, quantidade) -> {
            if (!isValidProduto(id, quantidade))
                throw new IllegalArgumentException("dados dos produtos inválidos");
        });

        this.clienteId = clienteId;
        this.status = status;
        this.preco = 0.0;
        this.produtos = produtos;
        this.dataAbertura = LocalDateTime.now();
    }

}
