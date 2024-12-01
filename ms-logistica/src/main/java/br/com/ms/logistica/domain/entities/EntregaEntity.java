package br.com.ms.logistica.domain.entities;

import br.com.ms.logistica.domain.enums.StatusEntrega;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static br.com.ms.logistica.domain.utils.Validator.*;

@Getter
@Setter
@NoArgsConstructor
public class EntregaEntity {

    private Long id;
    private StatusEntrega status;
    private Long pedidoId;
    private Long clienteId;
    private String destinatario;
    private Integer ddd;
    private Long telefone;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String uf;
    private Long cep;
    private EntregadorEntity entregador;

    public EntregaEntity(StatusEntrega status, Long pedidoId, Long clienteId) {
        if (!isValidObject(status))
            throw new IllegalArgumentException("status da entrega inválido");
        if (!isValidNumber(pedidoId))
            throw new IllegalArgumentException("identificador do pedido inválido");
        if (!isValidNumber(clienteId))
            throw new IllegalArgumentException("identificador do cliente inválido");

        this.status = status;
        this.pedidoId = pedidoId;
        this.clienteId = clienteId;
    }

}
