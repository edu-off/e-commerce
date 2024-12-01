package br.com.ms.cliente.performance;

import br.com.ms.cliente.application.dto.ClienteDTO;
import br.com.ms.cliente.application.dto.EnderecoDTO;
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
            .baseUrl("http://localhost:8081/e-commerce")
            .header("Content-Type", "application/json");

    private final EnderecoDTO enderecoDTO = new EnderecoDTO(null, "logradouro", "bairro", "cidade", "SP", 1L);
    private final ClienteDTO clienteDTO = new ClienteDTO(null, "nome", "ATIVO", "teste@teste.com.br", 1, 1L, enderecoDTO);

    ActionBuilder registrarClienteRequest = http("registrar cliente")
            .post("/cliente")
            .body(StringBody(asJsonString(clienteDTO)))
            .check(status().is(201))
            .check(jsonPath("$.id").saveAs("clienteId"));

    ActionBuilder atualizarClienteRequest = http("atualizar cliente")
            .put("/cliente/#{clienteId}")
            .body(StringBody(asJsonString(clienteDTO)))
            .check(status().is(200));

    ActionBuilder ativarClienteRequest = http("ativar cliente")
            .put("/cliente/ativa/#{clienteId}")
            .check(status().is(204));

    ActionBuilder inativarClienteRequest = http("inativar cliente")
            .put("/cliente/inativa/#{clienteId}")
            .check(status().is(204));

    ActionBuilder buscarClientePorId = http("buscar cliente por id")
            .get("/cliente/#{clienteId}")
            .check(status().is(200));

    ActionBuilder buscarClientePorNome = http("buscar cliente por nome")
            .get("/cliente/nome/nome")
            .check(status().is(200));

    ActionBuilder buscarClientePorEmail = http("buscar cliente por email")
            .get("/cliente/email/teste")
            .check(status().is(200));

    ActionBuilder buscarClientePorStatus = http("buscar cliente por status")
            .get("/cliente/status/ATIVO")
            .check(status().is(200));

    ScenarioBuilder cenarioRegistrarCliente = scenario("Registrar cliente")
            .exec(registrarClienteRequest);

    ScenarioBuilder cenarioAtualizarCliente = scenario("Atualizar cliente")
            .exec(registrarClienteRequest)
            .exec(atualizarClienteRequest);

    ScenarioBuilder cenarioAtivarCliente = scenario("Ativar cliente")
            .exec(registrarClienteRequest)
            .exec(ativarClienteRequest);

    ScenarioBuilder cenarioinativarCliente = scenario("Inativar cliente")
            .exec(registrarClienteRequest)
            .exec(inativarClienteRequest);

    ScenarioBuilder cenarioBuscarClientePorId = scenario("Buscar cliente por id")
            .exec(registrarClienteRequest)
            .exec(buscarClientePorId);

    ScenarioBuilder cenarioBuscarClientePorNome = scenario("Buscar cliente por nome")
            .exec(registrarClienteRequest)
            .exec(buscarClientePorNome);

    ScenarioBuilder cenarioBuscarClientePorEmail = scenario("Buscar cliente por email")
            .exec(registrarClienteRequest)
            .exec(buscarClientePorEmail);

    ScenarioBuilder cenarioBuscarClientePorStatus = scenario("Buscar cliente por status")
            .exec(registrarClienteRequest)
            .exec(buscarClientePorStatus);

    {
        setUp(
                cenarioRegistrarCliente.injectOpen(
                        rampUsersPerSec(1).to(10).during(Duration.ofSeconds(10)),
                        constantUsersPerSec(10).during(Duration.ofSeconds(60)),
                        rampUsersPerSec(10).to(1).during(Duration.ofSeconds(10))),
                cenarioAtualizarCliente.injectOpen(
                        rampUsersPerSec(1).to(10).during(Duration.ofSeconds(10)),
                        constantUsersPerSec(10).during(Duration.ofSeconds(60)),
                        rampUsersPerSec(10).to(1).during(Duration.ofSeconds(10))),
                cenarioAtivarCliente.injectOpen(
                        rampUsersPerSec(1).to(10).during(Duration.ofSeconds(10)),
                        constantUsersPerSec(10).during(Duration.ofSeconds(60)),
                        rampUsersPerSec(10).to(1).during(Duration.ofSeconds(10))),
                cenarioinativarCliente.injectOpen(
                        rampUsersPerSec(1).to(10).during(Duration.ofSeconds(10)),
                        constantUsersPerSec(10).during(Duration.ofSeconds(60)),
                        rampUsersPerSec(10).to(1).during(Duration.ofSeconds(10))),
                cenarioBuscarClientePorId.injectOpen(
                        rampUsersPerSec(1).to(10).during(Duration.ofSeconds(10)),
                        constantUsersPerSec(10).during(Duration.ofSeconds(60)),
                        rampUsersPerSec(10).to(1).during(Duration.ofSeconds(10))),
                cenarioBuscarClientePorNome.injectOpen(
                        rampUsersPerSec(1).to(10).during(Duration.ofSeconds(10)),
                        constantUsersPerSec(10).during(Duration.ofSeconds(60)),
                        rampUsersPerSec(10).to(1).during(Duration.ofSeconds(10))),
                cenarioBuscarClientePorEmail.injectOpen(
                        rampUsersPerSec(1).to(10).during(Duration.ofSeconds(10)),
                        constantUsersPerSec(10).during(Duration.ofSeconds(60)),
                        rampUsersPerSec(10).to(1).during(Duration.ofSeconds(10))),
                cenarioBuscarClientePorStatus.injectOpen(
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
