package br.com.ms.logistica.adapters.gateways;

import br.com.ms.logistica.application.gateways.EntregadorGateway;
import br.com.ms.logistica.domain.entities.EntregadorEntity;
import br.com.ms.logistica.domain.enums.StatusEntregador;
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
public class EntregadorGatewayImplIT {

    @Autowired
    private EntregadorGateway entregadorGateway;

    private EntregadorEntity entregadorEntitySetup;

    @BeforeEach
    public void setUp() {
        entregadorEntitySetup = entregadorGateway.salvar(new EntregadorEntity(StatusEntregador.DISPONIVEL, "nome", "teste@teste.com.br"));
        entregadorGateway.salvar(new EntregadorEntity(StatusEntregador.DISPONIVEL, "nome", "teste@teste.com.br"));
        entregadorGateway.salvar(new EntregadorEntity(StatusEntregador.DISPONIVEL, "nome", "teste@teste.com.br"));
        entregadorGateway.salvar(new EntregadorEntity(StatusEntregador.DISPONIVEL, "nome", "teste@teste.com.br"));
    }

    @Nested
    public class SalvandoEntregador {

        @Test
        @DisplayName("Deve salvar entregador corretamente")
        public void deveSalvarEntregadorCorretamente() {
            EntregadorEntity entregadorEntity = new EntregadorEntity(StatusEntregador.DISPONIVEL, "nome", "teste@teste.com.br");
            EntregadorEntity entregadorEntityCreated = entregadorGateway.salvar(entregadorEntity);
            assertThat(entregadorEntityCreated).isInstanceOf(EntregadorEntity.class).isNotNull();
            assertThat(entregadorEntityCreated.getId()).isNotNull().isNotZero();
        }

    }

    @Nested
    public class AtualizandoEntregador {

        @Test
        @DisplayName("Deve lançar exceçâo para entregador inexistente")
        public void deveLancarExcecaoParaEntregadorInexistente() {
            EntregadorEntity entregadorEntity = new EntregadorEntity(StatusEntregador.DISPONIVEL, "nome", "teste@teste.com.br");
            assertThatThrownBy(() -> entregadorGateway.atualizar(0L, entregadorEntity))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("entregador não encontrado");
        }

        @Test
        @DisplayName("Deve atualizar entregador corretamente")
        public void deveAtualizarEntregadorCorretamente() {
            EntregadorEntity entregadorEntity = new EntregadorEntity(StatusEntregador.DISPONIVEL, "nome", "teste@teste.com.br");
            EntregadorEntity entregadorEntityUpdated = entregadorGateway.atualizar(entregadorEntitySetup.getId(), entregadorEntity);
            assertThat(entregadorEntityUpdated).isInstanceOf(EntregadorEntity.class).isNotNull();
            assertThat(entregadorEntityUpdated.getId()).isEqualTo(entregadorEntitySetup.getId());
            assertThat(entregadorEntityUpdated.getStatus()).isEqualTo(entregadorEntity.getStatus());
            assertThat(entregadorEntityUpdated.getNome()).isEqualTo(entregadorEntitySetup.getNome());
            assertThat(entregadorEntityUpdated.getEmail()).isEqualTo(entregadorEntitySetup.getEmail());
        }

    }

    @Nested
    public class BuscandoEntregadores {

        @Test
        @DisplayName("Deve buscar entregador por id")
        public void deveBuscarEntregadorPorId() {
            EntregadorEntity entregadorEntity = entregadorGateway.buscarPorId(entregadorEntitySetup.getId());
            assertThat(entregadorEntity).isInstanceOf(EntregadorEntity.class).isNotNull();
            assertThat(entregadorEntity.getId()).isEqualTo(entregadorEntitySetup.getId());
            assertThat(entregadorEntity.getStatus()).isEqualTo(entregadorEntitySetup.getStatus());
            assertThat(entregadorEntity.getNome()).isEqualTo(entregadorEntitySetup.getNome());
            assertThat(entregadorEntity.getEmail()).isEqualTo(entregadorEntitySetup.getEmail());
        }

        @Test
        @DisplayName("Deve buscar entregadores por status")
        public void deveBuscarEntregadoresPorStatus() {
            List<EntregadorEntity> entregadores = entregadorGateway.buscarPorStatus(StatusEntregador.DISPONIVEL);
            assertThat(entregadores).isInstanceOf(List.class).isNotNull().isNotEmpty().hasSize(4);
        }

    }

}
