package br.com.ms.logistica.adapters.gateways;

import br.com.ms.logistica.application.gateways.EntregaGateway;
import br.com.ms.logistica.domain.entities.EntregaEntity;
import br.com.ms.logistica.domain.enums.StatusEntrega;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
@AutoConfigureTestDatabase
public class EntregaGatewayImplIT {

    @Autowired
    private EntregaGateway entregaGateway;

    private EntregaEntity entregaEntitySetup;

    @BeforeEach
    public void setUp() {
        entregaEntitySetup = entregaGateway.salvar(new EntregaEntity(StatusEntrega.PENDENTE, 1L, 1L));
        entregaGateway.salvar(new EntregaEntity(StatusEntrega.PENDENTE, 1L, 1L));
        entregaGateway.salvar(new EntregaEntity(StatusEntrega.PENDENTE, 1L, 1L));
        entregaGateway.salvar(new EntregaEntity(StatusEntrega.PENDENTE, 1L, 1L));
    }

    @Nested
    public class SalvandoEntrega {

        @Test
        @DisplayName("Deve salvar entrega corretamente")
        public void deveSalvarEntregaCorretamente() {
            EntregaEntity entregaEntity = new EntregaEntity(StatusEntrega.PENDENTE, 1L, 1L);
            EntregaEntity entregaEntityCreated = entregaGateway.salvar(entregaEntity);
            assertThat(entregaEntityCreated).isInstanceOf(EntregaEntity.class).isNotNull();
            assertThat(entregaEntityCreated.getId()).isNotNull().isNotZero();
        }

    }

    @Nested
    public class AtualizandoEntrega {

        @Test
        @DisplayName("Deve lançar exceçâo para entrega inexistente")
        public void deveLancarExcecaoParaEntregaInexistente() {
            EntregaEntity entregaEntity = new EntregaEntity(StatusEntrega.PENDENTE, 1L, 1L);
            assertThatThrownBy(() -> entregaGateway.atualizar(0L, entregaEntity))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("entrega não encontrada");
        }

        @Test
        @DisplayName("Deve atualizar entrega corretamente")
        public void deveAtualizarEntregaCorretamente() {
            EntregaEntity entregaEntity = new EntregaEntity(StatusEntrega.EM_TRANSITO, 2L, 2L);
            EntregaEntity entregaEntityUpdated = entregaGateway.atualizar(entregaEntitySetup.getId(), entregaEntity);
            assertThat(entregaEntityUpdated).isInstanceOf(EntregaEntity.class).isNotNull();
            assertThat(entregaEntityUpdated.getId()).isEqualTo(entregaEntitySetup.getId());
            assertThat(entregaEntityUpdated.getStatus()).isEqualTo(entregaEntity.getStatus());
            assertThat(entregaEntityUpdated.getPedidoId()).isEqualTo(entregaEntitySetup.getPedidoId());
            assertThat(entregaEntityUpdated.getClienteId()).isEqualTo(entregaEntitySetup.getClienteId());
        }

    }

    @Nested
    public class BuscandoEntregas {

        @Test
        @DisplayName("Deve buscar entrega por id")
        public void deveBuscarEntregaPorId() {
            EntregaEntity entregaEntity = entregaGateway.buscarPorId(entregaEntitySetup.getId());
            assertThat(entregaEntity).isInstanceOf(EntregaEntity.class).isNotNull();
            assertThat(entregaEntity.getId()).isEqualTo(entregaEntitySetup.getId());
            assertThat(entregaEntity.getStatus()).isEqualTo(entregaEntitySetup.getStatus());
            assertThat(entregaEntity.getPedidoId()).isEqualTo(entregaEntitySetup.getPedidoId());
            assertThat(entregaEntity.getClienteId()).isEqualTo(entregaEntitySetup.getClienteId());
        }

        @Test
        @DisplayName("Deve buscar entregas por status")
        public void deveBuscarEntregasPorStatus() {
            List<EntregaEntity> entregas = entregaGateway.buscarPorStatus(StatusEntrega.PENDENTE);
            assertThat(entregas).isInstanceOf(List.class).isNotNull().isNotEmpty().hasSize(4);
        }

    }

}
