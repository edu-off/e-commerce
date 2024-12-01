package br.com.ms.produto.performance;

import br.com.ms.produto.application.dto.ProdutoDTO;
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
            .baseUrl("http://localhost:8082/e-commerce")
            .header("Content-Type", "application/json");

    private final ProdutoDTO produtoDTO = new ProdutoDTO(null, "nome", "descricao", 1.1, 1000000, "categoria");

    ActionBuilder registrarProdutoRequest = http("registrar produto")
            .post("/produto")
            .body(StringBody(asJsonString(produtoDTO)))
            .check(status().is(201))
            .check(jsonPath("$.id").saveAs("produtoId"));

    ActionBuilder atualizarProdutoRequest = http("atualizar produto")
            .put("/produto/#{produtoId}")
            .body(StringBody(asJsonString(produtoDTO)))
            .check(status().is(200));

    ActionBuilder incrementarEstoqueProdutoRequest = http("incrementar estoque de produto")
            .put("/produto/#{produtoId}/estoque/adiciona/1")
            .check(status().is(200));

    ActionBuilder decrementarEstoqueProdutoRequest = http("decrementar estoque de produto")
            .put("/produto/#{produtoId}/estoque/remove/1")
            .check(status().is(200));

    ActionBuilder removerProdutoRequest = http("remover produto")
            .delete("/produto/#{produtoId}")
            .check(status().is(204));

    ActionBuilder buscarProdutoPorId = http("buscar produto por id")
            .get("/produto/#{produtoId}")
            .check(status().is(200));

    ActionBuilder buscarProdutoPorNome = http("buscar produto por nome")
            .get("/produto/nome/nome")
            .check(status().is(200));

    ActionBuilder buscarProdutoPorDescricao = http("buscar produto por descricao")
            .get("/produto/descricao/descricao")
            .check(status().is(200));

    ActionBuilder buscarProdutoPorCategoria = http("buscar produto por categoria")
            .get("/produto/categoria/categoria")
            .check(status().is(200));

    ScenarioBuilder cenarioRegistrarProduto = scenario("Registrar produto")
            .exec(registrarProdutoRequest);

    ScenarioBuilder cenarioAtualizarProduto = scenario("Atualizar produto")
            .exec(registrarProdutoRequest)
            .exec(atualizarProdutoRequest);

    ScenarioBuilder cenarioIncrementarEstoqueProduto = scenario("Incrementar estoque produto")
            .exec(registrarProdutoRequest)
            .exec(incrementarEstoqueProdutoRequest);

    ScenarioBuilder cenarioDecrementarEstoqueProduto = scenario("Decrementar estoque produto")
            .exec(registrarProdutoRequest)
            .exec(decrementarEstoqueProdutoRequest);

    ScenarioBuilder cenarioRemoverProduto = scenario("Remover produto")
            .exec(registrarProdutoRequest)
            .exec(removerProdutoRequest);

    ScenarioBuilder cenarioBuscarProdutoPorId = scenario("Buscar produto por id")
            .exec(registrarProdutoRequest)
            .exec(buscarProdutoPorId);

    ScenarioBuilder cenarioBuscarProdutoPorNome = scenario("Buscar produto por nome")
            .exec(registrarProdutoRequest)
            .exec(buscarProdutoPorNome);

    ScenarioBuilder cenarioBuscarProdutoPorDescricao = scenario("Buscar produto por descricao")
            .exec(registrarProdutoRequest)
            .exec(buscarProdutoPorDescricao);

    ScenarioBuilder cenarioBuscarProdutoPorCategoria = scenario("Buscar produto por categoria")
            .exec(registrarProdutoRequest)
            .exec(buscarProdutoPorCategoria);

    {
        setUp(
                cenarioRegistrarProduto.injectOpen(
                        rampUsersPerSec(1).to(10).during(Duration.ofSeconds(10)),
                        constantUsersPerSec(10).during(Duration.ofSeconds(60)),
                        rampUsersPerSec(10).to(1).during(Duration.ofSeconds(10))),
                cenarioAtualizarProduto.injectOpen(
                        rampUsersPerSec(1).to(10).during(Duration.ofSeconds(10)),
                        constantUsersPerSec(10).during(Duration.ofSeconds(60)),
                        rampUsersPerSec(10).to(1).during(Duration.ofSeconds(10))),
                cenarioIncrementarEstoqueProduto.injectOpen(
                        rampUsersPerSec(1).to(10).during(Duration.ofSeconds(10)),
                        constantUsersPerSec(10).during(Duration.ofSeconds(60)),
                        rampUsersPerSec(10).to(1).during(Duration.ofSeconds(10))),
                cenarioDecrementarEstoqueProduto.injectOpen(
                        rampUsersPerSec(1).to(10).during(Duration.ofSeconds(10)),
                        constantUsersPerSec(10).during(Duration.ofSeconds(60)),
                        rampUsersPerSec(10).to(1).during(Duration.ofSeconds(10))),
                cenarioRemoverProduto.injectOpen(
                        rampUsersPerSec(1).to(10).during(Duration.ofSeconds(10)),
                        constantUsersPerSec(10).during(Duration.ofSeconds(60)),
                        rampUsersPerSec(10).to(1).during(Duration.ofSeconds(10))),
                cenarioBuscarProdutoPorId.injectOpen(
                        rampUsersPerSec(1).to(10).during(Duration.ofSeconds(10)),
                        constantUsersPerSec(10).during(Duration.ofSeconds(60)),
                        rampUsersPerSec(10).to(1).during(Duration.ofSeconds(10))),
                cenarioBuscarProdutoPorNome.injectOpen(
                        rampUsersPerSec(1).to(10).during(Duration.ofSeconds(10)),
                        constantUsersPerSec(10).during(Duration.ofSeconds(60)),
                        rampUsersPerSec(10).to(1).during(Duration.ofSeconds(10))),
                cenarioBuscarProdutoPorDescricao.injectOpen(
                        rampUsersPerSec(1).to(10).during(Duration.ofSeconds(10)),
                        constantUsersPerSec(10).during(Duration.ofSeconds(60)),
                        rampUsersPerSec(10).to(1).during(Duration.ofSeconds(10))),
                cenarioBuscarProdutoPorCategoria.injectOpen(
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
