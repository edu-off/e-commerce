package br.com.ms.cliente.adapters.controllers;

import br.com.ms.cliente.adapters.presenters.ClientePresenter;
import br.com.ms.cliente.application.dto.ClienteDTO;
import br.com.ms.cliente.application.dto.EnderecoDTO;
import br.com.ms.cliente.application.usecases.*;
import br.com.ms.cliente.domain.entities.ClienteEntity;
import br.com.ms.cliente.domain.entities.EnderecoEntity;
import br.com.ms.cliente.domain.enums.StatusCliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;

public class BuscaClienteControllerTest {

    @Mock
    private BuscaCliente buscaCliente;

    @Mock
    private BuscaClientePorEmail buscaClientePorEmail;

    @Mock
    private BuscaClientePorNome buscaClientePorNome;

    @Mock
    private BuscaClientePorStatus buscaClientePorStatus;

    @Mock
    private BuscaEndereco buscaEndereco;

    @Mock
    private ClientePresenter presenter;

    @InjectMocks
    private BuscaClienteController buscaClienteController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class Buscas {

        @Test
        @DisplayName("deve buscar cliente por id")
        public void deveBuscarClientePorId() {
            EnderecoDTO enderecoDTO = new EnderecoDTO(null, "logradouro", "bairro", "cidade", "SP", 1L);
            ClienteDTO clienteDTO = new ClienteDTO(1L, "nome", "ATIVO", "teste@teste.com.br", 1, 1L, enderecoDTO);
            ClienteEntity clienteEntity = new ClienteEntity("nome", StatusCliente.ATIVO, "teste@teste.com.br", 1, 1L);
            EnderecoEntity enderecoEntity = new EnderecoEntity("logradouro", "bairro", "cidade", "SP", 1L, clienteEntity);
            Mockito.when(buscaCliente.execute(1L)).thenReturn(clienteEntity);
            Mockito.when(buscaEndereco.execute(1L)).thenReturn(enderecoEntity);
            Mockito.when(presenter.transform(clienteEntity, enderecoEntity)).thenReturn(clienteDTO);
            ClienteDTO clienteDTORetrieved = buscaClienteController.porId(1L);
            assertThat(clienteDTORetrieved).isInstanceOf(ClienteDTO.class).isNotNull();
        }

        @Test
        @DisplayName("deve buscar clientes por nome")
        public void deveBuscarClientesPorNome() {
            Pageable pageable = PageRequest.of(0, 10);
            Page<ClienteDTO> clientesDTO = Page.empty();
            Page<ClienteEntity> clientesEntity = Page.empty();
            Mockito.when(buscaClientePorNome.execute("nome", pageable)).thenReturn(clientesEntity);
            Mockito.when(presenter.transform(clientesEntity, pageable)).thenReturn(clientesDTO);
            Page<ClienteDTO> clientesDTORetrieved = buscaClienteController.porNome("nome", pageable);
            assertThat(clientesDTORetrieved).isInstanceOf(Page.class).isEmpty();
        }


        @Test
        @DisplayName("deve buscar clientes por e-mail")
        public void deveBuscarClientesPorEmail() {
            Pageable pageable = PageRequest.of(0, 10);
            Page<ClienteDTO> clientesDTO = Page.empty();
            Page<ClienteEntity> clientesEntity = Page.empty();
            Mockito.when(buscaClientePorEmail.execute("teste@teste.com.br", pageable)).thenReturn(clientesEntity);
            Mockito.when(presenter.transform(clientesEntity, pageable)).thenReturn(clientesDTO);
            Page<ClienteDTO> clientesDTORetrieved = buscaClienteController.porEmail("teste@teste.com.br", pageable);
            assertThat(clientesDTORetrieved).isInstanceOf(Page.class).isEmpty();
        }

        @Test
        @DisplayName("deve buscar clientes por status")
        public void deveBuscarClientesPorStatus() {
            Pageable pageable = PageRequest.of(0, 10);
            Page<ClienteDTO> clientesDTO = Page.empty();
            Page<ClienteEntity> clientesEntity = Page.empty();
            Mockito.when(buscaClientePorStatus.execute(StatusCliente.ATIVO.toString(), pageable)).thenReturn(clientesEntity);
            Mockito.when(presenter.transform(clientesEntity, pageable)).thenReturn(clientesDTO);
            Page<ClienteDTO> clientesDTORetrieved = buscaClienteController.porStatus(StatusCliente.ATIVO.toString(), pageable);
            assertThat(clientesDTORetrieved).isInstanceOf(Page.class).isEmpty();
        }

    }

}
