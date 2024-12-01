package br.com.ms.pedido.config;

import br.com.ms.pedido.adapters.handler.ExceptionResponse;
import br.com.ms.pedido.application.dto.ClienteDTO;
import br.com.ms.pedido.application.dto.EntregaDTO;
import br.com.ms.pedido.application.dto.ProdutoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

public class StubConfig {

    public static void recuperaProdutoMockResponseOK(Long id, Integer quantidade, Double preco) throws IOException {
        ProdutoDTO produtoDTO = new ProdutoDTO(id, quantidade, preco);

        stubFor(get(urlEqualTo("/e-commerce/produto/" + id))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(asJsonString(produtoDTO))));
    }

    public static void recuperaProdutoMockResponseNotFound(Long id) throws IOException {
        ExceptionResponse response = new ExceptionResponse(getTimestamp(), HttpStatus.NOT_FOUND.value(), "not found", "/e-commerce/produto/" + id);
        stubFor(get(urlEqualTo("/e-commerce/produto/" + id))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.NOT_FOUND.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(asJsonString(response))));
    }

    public static void incrementaEstoqueMockResponseOK(Long id, Integer quantidade) throws IOException {
        stubFor(put(urlEqualTo("/e-commerce/produto/" + id + "/estoque/adiciona/" + quantidade))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)));
    }

    public static void incrementaEstoqueMockResponseNotFound(Long id, Integer quantidade) throws IOException {
        ExceptionResponse response = new ExceptionResponse(getTimestamp(), HttpStatus.NOT_FOUND.value(), "not found", "/e-commerce/produto/" + id + "/estoque/adiciona/" + quantidade);
        stubFor(put(urlEqualTo("/e-commerce/produto/" + id + "/estoque/adiciona/" + quantidade))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.NOT_FOUND.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(asJsonString(response))));
    }

    public static void decrementaEstoqueMockResponseOK(Long id, Integer quantidade) throws IOException {
        stubFor(put(urlEqualTo("/e-commerce/produto/" + id + "/estoque/remove/" + quantidade))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)));
    }

    public static void decrementaEstoqueMockResponseNotFound(Long id, Integer quantidade) throws IOException {
        ExceptionResponse response = new ExceptionResponse(getTimestamp(), HttpStatus.NOT_FOUND.value(), "not found", "/e-commerce/produto/" + id + "/estoque/remove/" + quantidade);
        stubFor(put(urlEqualTo("/e-commerce/produto/" + id + "/estoque/remove/" + quantidade))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.NOT_FOUND.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(asJsonString(response))));
    }

    public static void registraEntregaMockResponseOK(Long clienteId, Long pedidoId) throws IOException {
        EntregaDTO entregaDTORequest = new EntregaDTO(null, "PENDENTE", clienteId, pedidoId);
        EntregaDTO entregaDTOresponse = new EntregaDTO(1L, "PENDENTE", clienteId, pedidoId);

        stubFor(post(urlEqualTo("/e-commerce/entrega"))
                .withRequestBody(equalToJson(asJsonString(entregaDTORequest)))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(asJsonString(entregaDTOresponse))));
    }

    public static void registraEntregaMockResponseBadRequest(Long clienteId, Long pedidoId) throws IOException {
        EntregaDTO entregaDTORequest = new EntregaDTO(null, "PENDENTE", clienteId, pedidoId);
        ExceptionResponse response = new ExceptionResponse(getTimestamp(), HttpStatus.BAD_REQUEST.value(), "bad request", "/e-commerce/entrega");

        stubFor(post(urlEqualTo("/e-commerce/entrega"))
                .withRequestBody(equalToJson(asJsonString(entregaDTORequest)))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.BAD_REQUEST.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(asJsonString(response))));
    }

    public static void recuperaClienteMockResponseOK(Long id) throws IOException {
        ClienteDTO clienteDTO = new ClienteDTO(id, "nome qualquer", "ATIVO", "teste@teste.com.br", 1, 1L);

        stubFor(get(urlEqualTo("/e-commerce/cliente/" + id))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(asJsonString(clienteDTO))));
    }

    public static void recuperaClienteMockResponseNotFound(Long id) throws IOException {
        ExceptionResponse response = new ExceptionResponse(getTimestamp(), HttpStatus.NOT_FOUND.value(), "not found", "/e-commerce/cliente/" + id);
        stubFor(get(urlEqualTo("/e-commerce/cliente/" + id))
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
