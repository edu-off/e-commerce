package br.com.ms.logistica.application.usecases;

import br.com.ms.logistica.application.dto.ClienteDTO;
import br.com.ms.logistica.application.dto.EnderecoDTO;
import br.com.ms.logistica.application.gateways.ClienteGateway;
import br.com.ms.logistica.domain.entities.EntregaEntity;
import br.com.ms.logistica.domain.enums.StatusEntrega;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

public class RecuperaDadosClienteTest {

    @Mock
    private ClienteGateway clienteGateway;

    @InjectMocks
    public RecuperaDadosCliente recuperaDadosCliente;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class RecuperandoDadosCliente {

        @Test
        @DisplayName("deve lancar excecao para cliente não encontrado")
        public void deveLancarExcecaoParaClienteNaoEncontrado() {
            EntregaEntity entregaEntity = new EntregaEntity(StatusEntrega.PENDENTE, 1L, 1L);
            when(clienteGateway.buscaPorId(1L)).thenReturn(null);
            assertThatThrownBy(() -> recuperaDadosCliente.porId(entregaEntity))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("cliente não encontrado");
        }


        @Test
        @DisplayName("deve recuperar dados do cliente corretamente")
        public void deveRecuperarDadosDoClienteCorretamente() {
            EnderecoDTO enderecoDTO = new EnderecoDTO(null, "logradouro", "bairro", "cidade", "SP", 1L);
            ClienteDTO clienteDTO = new ClienteDTO(null, "nome", "ATIVO", "teste@teste.com.br", 1, 1L, enderecoDTO);
            when(clienteGateway.buscaPorId(1L)).thenReturn(clienteDTO);
            EntregaEntity entregaEntityUpdated = recuperaDadosCliente.porId(new EntregaEntity(StatusEntrega.PENDENTE, 1L, 1L));
            assertThat(entregaEntityUpdated).isInstanceOf(EntregaEntity.class).isNotNull();
            assertThat(entregaEntityUpdated.getDestinatario()).isEqualTo("nome");
            assertThat(entregaEntityUpdated.getDdd()).isEqualTo(1);
            assertThat(entregaEntityUpdated.getTelefone()).isEqualTo(1L);
            assertThat(entregaEntityUpdated.getLogradouro()).isEqualTo("logradouro");
            assertThat(entregaEntityUpdated.getBairro()).isEqualTo("bairro");
            assertThat(entregaEntityUpdated.getCidade()).isEqualTo("cidade");
            assertThat(entregaEntityUpdated.getUf()).isEqualTo("SP");
            assertThat(entregaEntityUpdated.getCep()).isEqualTo(1L);
        }

    }

}
