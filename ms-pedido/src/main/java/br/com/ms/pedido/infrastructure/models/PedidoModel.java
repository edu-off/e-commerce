package br.com.ms.pedido.infrastructure.models;

import br.com.ms.pedido.domain.enums.StatusPedido;
import br.com.ms.pedido.infrastructure.config.JsonConverterConfig;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "pedido")
@NoArgsConstructor
@AllArgsConstructor
public class PedidoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cliente_id")
    private Long clienteId;

    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    private Double preco;

    @Column(columnDefinition = "json")
    @Convert(converter = JsonConverterConfig.class)
    private List<Map<String, Object>> produtos;

    @Column(name = "data_abertura")
    private LocalDateTime dataAbertura;

    @Column(name = "data_confirmacao")
    private LocalDateTime dataConfirmacao;

    @Column(name = "data_cancelamento")
    private LocalDateTime dataCancelamento;

    @Column(name = "data_conclusao")
    private LocalDateTime dataConclusao;

}
