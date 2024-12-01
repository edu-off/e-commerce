package br.com.ms.logistica.infrastructure.models;

import br.com.ms.logistica.domain.enums.StatusEntregador;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "entregador")
public class EntregadorModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Enumerated(EnumType.STRING)
    private StatusEntregador status;

    private String email;

    @OneToMany(mappedBy = "entregador", fetch = FetchType.EAGER)
    private List<EntregaModel> entregas;

}
