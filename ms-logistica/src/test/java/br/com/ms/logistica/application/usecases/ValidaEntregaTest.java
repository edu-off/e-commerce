package br.com.ms.logistica.application.usecases;

import br.com.ms.logistica.application.dto.EntregaDTO;
import br.com.ms.logistica.application.exceptions.EntregaException;
import br.com.ms.logistica.domain.entities.EntregaEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ValidaEntregaTest {

    private final ValidaEntrega validaEntrega = new ValidaEntrega();

    @Nested
    public class ValidandoEntrega {

        @Test
        @DisplayName("Deve validar entrega corretamente")
        public void deveValidarEntregaCorretamente() {
            EntregaDTO entregaDTO = new EntregaDTO(null, "PENDENTE", 1L, 1L);
            EntregaEntity entregaEntity = validaEntrega.run(entregaDTO);
            assertThat(entregaEntity).isInstanceOf(EntregaEntity.class).isNotNull();
        }

        @Test
        @DisplayName("Deve lançar exceção para entrega inválida")
        public void deveLancarExcecaoParaEntregaInvalida() {
            EntregaDTO entregaDTO = new EntregaDTO(null, "", 1L, 1L);
            assertThatThrownBy(() -> validaEntrega.run(entregaDTO))
                    .isInstanceOf(EntregaException.class)
                    .hasMessage("erro ao validar entrega: status inválido");
        }

    }

}
