package br.com.ms.logistica.performance;

import br.com.ms.logistica.application.dto.ClienteDTO;
import br.com.ms.logistica.application.dto.EnderecoDTO;
import br.com.ms.logistica.application.dto.EntregaDTO;
import br.com.ms.logistica.application.dto.EntregadorDTO;
import br.com.ms.logistica.utils.CreateProdutoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.gatling.javaapi.core.ActionBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class PerformanceSimulation extends Simulation {

    private final HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:")
            .header("Content-Type", "application/json");

    private final EnderecoDTO enderecoDTO = new EnderecoDTO(null, "logradouro", "bairro", "cidade", "SP", 1L);
    private final ClienteDTO clienteDTO = new ClienteDTO(null, "nome", "ATIVO", "teste@teste.com.br", 1, 1L, enderecoDTO);
    private final CreateProdutoDTO produtoDTO = new CreateProdutoDTO(null, "nome", "descricao", 10.99, 2000, "categoria");
    private final EntregaDTO entregaDTO = new EntregaDTO(null, "PENDENTE", 1L, 1L);
    private final EntregadorDTO entregadorDTO = new EntregadorDTO(null, "DISPONIVEL", "nome", "teste@teste.com.br");

    ActionBuilder registrarClienteRequest = http("registrar cliente")
            .post("8081/e-commerce/cliente")
            .body(StringBody(asJsonString(clienteDTO)))
            .check(status().is(201))
            .check(jsonPath("$.id").saveAs("clienteId"));

    ActionBuilder registrarProdutoRequest = http("registrar produto")
            .post("8082/e-commerce/produto")
            .body(StringBody(asJsonString(produtoDTO)))
            .check(status().is(201))
            .check(jsonPath("$.id").saveAs("produtoId"));

    ActionBuilder registrarPedidoRequest = http("registrar pedido")
            .post("8083/e-commerce/pedido")
            .body(StringBody("{\"clienteId\": #{clienteId},\"status\": \"EM_ABERTO\",\"produtos\": [{\"id\": #{produtoId},\"quantidade\": 1}]}"))
            .check(status().is(201))
            .check(jsonPath("$.id").saveAs("pedidoId"));

    ActionBuilder registrarEntregaRequest = http("registrar entrega")
            .post("8084/e-commerce/entrega")
            .body(StringBody(asJsonString(entregaDTO)))
            .check(status().is(201))
            .check(jsonPath("$.id").saveAs("entregaId"));

    ActionBuilder registrarEntregadorRequest = http("registrar entregador")
            .post("8084/e-commerce/entregador")
            .body(StringBody(asJsonString(entregadorDTO)))
            .check(status().is(201))
            .check(jsonPath("$.id").saveAs("entregadorId"));

    ActionBuilder atualizarEntregadorRequest = http("atualizar entregador")
            .put("8084/e-commerce/entregador/#{entregadorId}")
            .body(StringBody(asJsonString(entregadorDTO)))
            .check(status().is(200));

    ScenarioBuilder cenarioRegistrarEntrega = scenario("Registrar entrega")
            .exec(registrarClienteRequest)
            .exec(registrarProdutoRequest)
            .exec(registrarPedidoRequest)
            .exec(registrarEntregaRequest);

    ScenarioBuilder cenarioRegistrarEntregador = scenario("Registrar entregador")
            .exec(registrarEntregadorRequest);

    ScenarioBuilder cenarioAtualizarEntregador = scenario("Atualizar entregador")
            .exec(registrarEntregadorRequest)
            .exec(atualizarEntregadorRequest);

    {
        setUp(
                cenarioRegistrarEntrega.injectOpen(
                        rampUsersPerSec(1).to(10).during(Duration.ofSeconds(10)),
                        constantUsersPerSec(10).during(Duration.ofSeconds(60)),
                        rampUsersPerSec(10).to(1).during(Duration.ofSeconds(10))),
                cenarioRegistrarEntregador.injectOpen(
                        rampUsersPerSec(1).to(10).during(Duration.ofSeconds(10)),
                        constantUsersPerSec(10).during(Duration.ofSeconds(60)),
                        rampUsersPerSec(10).to(1).during(Duration.ofSeconds(10))),
                cenarioAtualizarEntregador.injectOpen(
                        rampUsersPerSec(1).to(10).during(Duration.ofSeconds(10)),
                        constantUsersPerSec(10).during(Duration.ofSeconds(60)),
                        rampUsersPerSec(10).to(1).during(Duration.ofSeconds(10)))
        ).protocols(httpProtocol).assertions(
                global().responseTime().max().lt(750),
                global().failedRequests().count().is(0L));
    }

    private static String asJsonString(final Object obj) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
