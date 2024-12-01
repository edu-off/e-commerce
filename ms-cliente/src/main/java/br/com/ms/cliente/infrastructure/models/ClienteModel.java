package br.com.ms.cliente.infrastructure.models;

import br.com.ms.cliente.domain.enums.StatusCliente;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@Table(name = "cliente")
@NoArgsConstructor
@AllArgsConstructor
public class ClienteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Enumerated(EnumType.STRING)
    private StatusCliente status;

    private String email;

    private Integer ddd;

    private Long telefone;

    @OneToOne(mappedBy = "cliente")
    private EnderecoModel endereco;

}
