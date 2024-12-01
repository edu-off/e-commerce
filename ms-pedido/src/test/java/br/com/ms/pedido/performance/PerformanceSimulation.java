package br.com.ms.pedido.performance;

import br.com.ms.pedido.utils.CreateClienteDTO;
import br.com.ms.pedido.utils.CreateEnderecoDTO;
import br.com.ms.pedido.utils.CreateProdutoDTO;
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

    private final CreateEnderecoDTO endereco = new CreateEnderecoDTO(null, "logradouro", "bairro", "cidade", "SP", 1L);
    private final CreateClienteDTO cliente = new CreateClienteDTO(null, "nome", "ATIVO", "teste@teste.com.br", 1, 1L, endereco);
    private final CreateProdutoDTO produto = new CreateProdutoDTO(null, "nome", "descricao", 10.99, 2000, "categoria");

    ActionBuilder registrarClienteRequest = http("registrar cliente")
            .post("8081/e-commerce/cliente")
            .body(StringBody(asJsonString(cliente)))
            .check(status().is(201))
            .check(jsonPath("$.id").saveAs("clienteId"));

    ActionBuilder registrarPrimeiroProdutoRequest = http("registrar primeiro produto")
            .post("8082/e-commerce/produto")
            .body(StringBody(asJsonString(produto)))
            .check(status().is(201))
            .check(jsonPath("$.id").saveAs("primeiroProdutoId"));

    ActionBuilder registrarSegundoProdutoRequest = http("registrar segundo produto")
            .post("8082/e-commerce/produto")
            .body(StringBody(asJsonString(produto)))
            .check(status().is(201))
            .check(jsonPath("$.id").saveAs("segundoProdutoId"));

    ActionBuilder registrarPedidoRequest = http("registrar pedido")
            .post("8083/e-commerce/pedido")
            .body(StringBody("{\"clienteId\": #{clienteId},\"status\": \"EM_ABERTO\",\"produtos\": [{\"id\": #{primeiroProdutoId},\"quantidade\": 1}]}"))
            .check(status().is(201))
            .check(jsonPath("$.id").saveAs("pedidoId"));

    ActionBuilder atualizarListaProdutosRequest = http("atualizar lista produtos")
            .put("8083/e-commerce/pedido/#{pedidoId}/produtos")
            .body(StringBody("[{\"id\": #{primeiroProdutoId},\"quantidade\": 1}, {\"id\": #{segundoProdutoId},\"quantidade\": 1}]"))
            .check(status().is(200));

    ActionBuilder confirmarPedidoRequest = http("confirmar pedido")
            .put("8083/e-commerce/pedido/confirmacao/#{pedidoId}")
            .check(status().is(204));

    ActionBuilder cancelarPedidoRequest = http("cancelar pedido")
            .put("8083/e-commerce/pedido/cancelamento/#{pedidoId}")
            .check(status().is(204));

    ActionBuilder concluirPedidoRequest = http("concluir pedido")
            .put("8083/e-commerce/pedido/conclusao/#{pedidoId}")
            .check(status().is(204));

    ScenarioBuilder cenarioRegistrarPedido = scenario("Registrar pedido")
            .exec(registrarClienteRequest)
            .exec(registrarPrimeiroProdutoRequest)
            .exec(registrarPedidoRequest);

    ScenarioBuilder cenarioAtualizarListaProdutos = scenario("Atualizar lista de produtos do pedido")
            .exec(registrarClienteRequest)
            .exec(registrarPrimeiroProdutoRequest)
            .exec(registrarSegundoProdutoRequest)
            .exec(registrarPedidoRequest)
            .exec(atualizarListaProdutosRequest);

    ScenarioBuilder cenarioConfirmarPedido = scenario("Confirmar pedido")
            .exec(registrarClienteRequest)
            .exec(registrarPrimeiroProdutoRequest)
            .exec(registrarPedidoRequest)
            .exec(confirmarPedidoRequest);

    ScenarioBuilder cenarioCancelarPedido = scenario("Cancelar pedido")
            .exec(registrarClienteRequest)
            .exec(registrarPrimeiroProdutoRequest)
            .exec(registrarPedidoRequest)
            .exec(cancelarPedidoRequest);

    ScenarioBuilder cenarioConcluirPedido = scenario("Concluir pedido")
            .exec(registrarClienteRequest)
            .exec(registrarPrimeiroProdutoRequest)
            .exec(registrarPedidoRequest)
            .exec(confirmarPedidoRequest)
            .exec(concluirPedidoRequest);

    {
        setUp(
                cenarioRegistrarPedido.injectOpen(
                        rampUsersPerSec(1).to(10).during(Duration.ofSeconds(10)),
                        constantUsersPerSec(10).during(Duration.ofSeconds(60)),
                        rampUsersPerSec(10).to(1).during(Duration.ofSeconds(10))),
                cenarioAtualizarListaProdutos.injectOpen(
                        rampUsersPerSec(1).to(10).during(Duration.ofSeconds(10)),
                        constantUsersPerSec(10).during(Duration.ofSeconds(60)),
                        rampUsersPerSec(10).to(1).during(Duration.ofSeconds(10))),
                cenarioConfirmarPedido.injectOpen(
                        rampUsersPerSec(1).to(10).during(Duration.ofSeconds(10)),
                        constantUsersPerSec(10).during(Duration.ofSeconds(60)),
                        rampUsersPerSec(10).to(1).during(Duration.ofSeconds(10))),
                cenarioCancelarPedido.injectOpen(
                        rampUsersPerSec(1).to(10).during(Duration.ofSeconds(10)),
                        constantUsersPerSec(10).during(Duration.ofSeconds(60)),
                        rampUsersPerSec(10).to(1).during(Duration.ofSeconds(10))),
                cenarioConcluirPedido.injectOpen(
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
