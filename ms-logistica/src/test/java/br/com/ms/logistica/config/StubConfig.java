package br.com.ms.logistica.config;

import br.com.ms.logistica.adapters.handler.ExceptionResponse;
import br.com.ms.logistica.application.dto.ClienteDTO;
import br.com.ms.logistica.application.dto.EnderecoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class StubConfig {

    public static void recuperaClienteMockResponseOK(Long id) throws IOException {
        EnderecoDTO enderecoDTO = new EnderecoDTO(null, "logradouro", "bairro", "cidade", "SP", 1L);
        ClienteDTO clienteDTO = new ClienteDTO(null, "nome", "ATIVO", "teste@teste.com.br", 1, 1L, enderecoDTO);

        stubFor(get(urlEqualTo("/e-commerce/cliente/" + id))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(asJsonString(clienteDTO))));

    }

    public static void recuperaClienteMockResponseNotFound(Long id) throws IOException {
        ExceptionResponse response = new ExceptionResponse(getTimestamp(), HttpStatus.NOT_FOUND.value(), "not found", "/e-commerce/cliente/");
        stubFor(get(urlEqualTo("/e-commerce/cliente/" + id))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.NOT_FOUND.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(asJsonString(response))));
    }

    public static void concluiPedidoMockResponseOK(Long id) throws IOException {
        stubFor(put(urlEqualTo("/e-commerce/pedido/conclusao/" + id))
                .willReturn(aResponse().withStatus(HttpStatus.NO_CONTENT.value())));
    }

    public static void concluiPedidoMockResponseNotFound(Long id) throws IOException {
        ExceptionResponse response = new ExceptionResponse(getTimestamp(), HttpStatus.NOT_FOUND.value(), "not found", "/e-commerce/pedido/conclusao/");
        stubFor(put(urlEqualTo("/e-commerce/pedido/conclusao/" + id))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.NOT_FOUND.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(asJsonString(response))));
    }

    public static void cancelaPedidoMockResponseOK(Long id) throws IOException {
        stubFor(put(urlEqualTo("/e-commerce/pedido/cancelamento/" + id))
                .willReturn(aResponse().withStatus(HttpStatus.NO_CONTENT.value())));
    }

    public static void cancelaPedidoMockResponseNotFound(Long id) throws IOException {
        ExceptionResponse response = new ExceptionResponse(getTimestamp(), HttpStatus.NOT_FOUND.value(), "not found", "/e-commerce/pedido/cancelamento/");
        stubFor(put(urlEqualTo("/e-commerce/pedido/cancelamento/" + id))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.NOT_FOUND.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(asJsonString(response))));
    }

    private static String asJsonString(final Object obj) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String getTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }

}
