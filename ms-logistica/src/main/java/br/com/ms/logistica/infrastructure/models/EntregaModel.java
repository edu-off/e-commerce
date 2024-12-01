package br.com.ms.logistica.infrastructure.models;

import br.com.ms.logistica.domain.enums.StatusEntrega;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "entrega")
public class EntregaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private StatusEntrega status;

    @Column(name = "pedido_id")
    private Long pedidoId;

    @Column(name = "cliente_id")
    private Long clienteId;

    private String destinatario;

    private Integer ddd;

    private Long telefone;

    private String logradouro;

    private String bairro;

    private String cidade;

    private String uf;

    private Long cep;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "entregador_id", referencedColumnName = "id")
    private EntregadorModel entregador;

}
