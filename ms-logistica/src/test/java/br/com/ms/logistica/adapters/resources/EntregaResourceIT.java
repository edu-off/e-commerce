package br.com.ms.logistica.adapters.resources;

import br.com.ms.logistica.application.dto.EntregaDTO;
import br.com.ms.logistica.config.StubConfig;
import br.com.ms.logistica.domain.enums.StatusEntrega;
import br.com.ms.logistica.domain.enums.StatusEntregador;
import br.com.ms.logistica.infrastructure.models.EntregaModel;
import br.com.ms.logistica.infrastructure.models.EntregadorModel;
import br.com.ms.logistica.infrastructure.repositories.EntregaRepository;
import br.com.ms.logistica.infrastructure.repositories.EntregadorRepository;
import io.cucumber.java.pt.E;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.NoSuchElementException;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

@ActiveProfiles("test")
@AutoConfigureTestDatabase
@AutoConfigureWireMock(port = 0)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EntregaResourceIT {

    @LocalServerPort
    private int port;

    @Autowired
    private EntregaRepository entregaRepository;

    @Autowired
    private EntregadorRepository entregadorRepository;

    private long id;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        EntregaModel EntregaModel = new EntregaModel(null, StatusEntrega.PENDENTE, 1L, 1L, null, null, null, null, null, null, null, null, null);
        EntregaModel entregaModelCreated = entregaRepository.save(EntregaModel);
        id = entregaModelCreated.getId();
    }

    @Nested
    public class ValidacaoRegistroEntrega {

        @Test
        @DisplayName("Deve retornar unprocessable entity para registro de entrega inválida")
        public void deveRetornarUnprocessableEntityParaRegistroDeEntregaInvalida() {
            EntregaDTO EntregaDTO = new EntregaDTO(null, "PENDENTE", 0L, 1L);
            given().contentType(MediaType.APPLICATION_JSON_VALUE).body(EntregaDTO)
                    .when().post("/e-commerce/entrega")
                    .then().statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                    .body("$", hasKey("message"))
                    .body("message", equalTo("Existem dados incorretos no corpo da requisição"));
        }

        @Test
        @DisplayName("Deve retornar not found para cliente inexistente")
        public void deveRetornarNotFoundParaClienteInexistente() throws IOException {
            StubConfig.recuperaClienteMockResponseNotFound(1L);
            EntregaDTO EntregaDTO = new EntregaDTO(null, "PENDENTE", 1L, 1L);
            given().contentType(MediaType.APPLICATION_JSON_VALUE).body(EntregaDTO)
                    .when().post("/e-commerce/entrega")
                    .then().statusCode(HttpStatus.NOT_FOUND.value());
        }

        @Test
        @DisplayName("Deve retornar not found para pedido inexistente")
        public void deveRetornarNotFoundParaPedidoInexistente() throws IOException {
            StubConfig.recuperaClienteMockResponseOK(1L);
            StubConfig.recuperaPedidoMockResponseNotFound(1L);
            EntregaDTO EntregaDTO = new EntregaDTO(null, "PENDENTE", 1L, 1L);
            given().contentType(MediaType.APPLICATION_JSON_VALUE).body(EntregaDTO)
                    .when().post("/e-commerce/entrega")
                    .then().statusCode(HttpStatus.NOT_FOUND.value());
        }

        @Test
        @DisplayName("Deve registrar entrega")
        public void deveRegistrarCliente() throws IOException {
            StubConfig.recuperaClienteMockResponseOK(1L);
            StubConfig.recuperaPedidoMockResponseOK(1L, 1L);
            EntregaDTO EntregaDTO = new EntregaDTO(null, "PENDENTE", 1L, 1L);
            given().contentType(MediaType.APPLICATION_JSON_VALUE).body(EntregaDTO)
                    .when().post("/e-commerce/entrega")
                    .then().statusCode(HttpStatus.CREATED.value())
                    .body(matchesJsonSchemaInClasspath("./schemas/EntregaResponseSchema.json"));
        }

    }

    @Nested
    public class ValidacaoConclusaoEntrega {

        @Test
        @DisplayName("deve lancar excecao para entrega nao encontrada")
        public void deveLancarExcecaoParaEntregaNaoEncontrada() {
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().put("/e-commerce/entrega/conclusao/{id}", 0L)
                    .then().statusCode(HttpStatus.NOT_FOUND.value());
        }

        @Test
        @DisplayName("deve lancar excecao para pedido nao encontrado")
        public void deveLancarExcecaoParaPedidoNaoEncontrado() throws IOException {
            Optional<EntregaModel> optional = entregaRepository.findById(id);
            if (optional.isEmpty())
                throw new NoSuchElementException("entrega não encontrada");
            EntregaModel entregaModel = optional.get();
            entregaModel.setStatus(StatusEntrega.EM_TRANSITO);
            entregaRepository.save(entregaModel);
            StubConfig.concluiPedidoMockResponseNotFound(1L);
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().put("/e-commerce/entrega/conclusao/{id}", id)
                    .then().statusCode(HttpStatus.NOT_FOUND.value());
        }

        @Test
        @DisplayName("deve lancar excecao para entregador nao encontrado")
        public void deveLancarExcecaoParaEntregadorNaoEncontrado() throws IOException {
            Optional<EntregaModel> optional = entregaRepository.findById(id);
            if (optional.isEmpty())
                throw new NoSuchElementException("entrega não encontrada");
            EntregaModel entregaModel = optional.get();
            entregaModel.setStatus(StatusEntrega.EM_TRANSITO);
            entregaRepository.save(entregaModel);
            StubConfig.concluiPedidoMockResponseOK(1L);
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().put("/e-commerce/entrega/conclusao/{id}", id)
                    .then().statusCode(HttpStatus.NOT_FOUND.value());
        }

        @Test
        @DisplayName("deve concluir entrega corretamente")
        public void deveConcluirEntregaCorretamente() throws IOException {
            StubConfig.concluiPedidoMockResponseOK(1L);
            EntregadorModel entregadorModel = entregadorRepository.save(new EntregadorModel(null, "nome", StatusEntregador.DISPONIVEL, "teste@teste.com.br", null));
            Optional<EntregaModel> optional = entregaRepository.findById(id);
            if (optional.isEmpty())
                throw new NoSuchElementException("entrega não encontrada");
            EntregaModel entregaModelReturned = optional.get();
            entregaModelReturned.setEntregador(entregadorModel);
            entregaModelReturned.setStatus(StatusEntrega.EM_TRANSITO);
            EntregaModel entregaModelUpdated = entregaRepository.save(entregaModelReturned);
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().put("/e-commerce/entrega/conclusao/{id}", entregaModelUpdated.getId())
                    .then().statusCode(HttpStatus.NO_CONTENT.value());
        }

    }

    @Nested
    public class ValidacaoCancelamentoEntrega {

        @Test
        @DisplayName("deve lancar excecao para entrega nao encontrada")
        public void deveLancarExcecaoParaEntregaNaoEncontrada() {
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().put("/e-commerce/entrega/cancelamento/{id}", 0L)
                    .then().statusCode(HttpStatus.NOT_FOUND.value());
        }

        @Test
        @DisplayName("deve lancar excecao para pedido nao encontrado")
        public void deveLancarExcecaoParaPedidoNaoEncontrado() throws IOException {
            StubConfig.cancelaPedidoMockResponseNotFound(1L);
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().put("/e-commerce/entrega/cancelamento/{id}", id)
                    .then().statusCode(HttpStatus.NOT_FOUND.value());
        }

        @Test
        @DisplayName("deve cancelar entrega corretamente")
        public void deveCancelarEntregaCorretamente() throws IOException {
            StubConfig.cancelaPedidoMockResponseOK(1L);
            EntregadorModel entregadorModel = entregadorRepository.save(new EntregadorModel(null, "nome", StatusEntregador.DISPONIVEL, "teste@teste.com.br", null));
            Optional<EntregaModel> optional = entregaRepository.findById(id);
            if (optional.isEmpty())
                throw new NoSuchElementException("entrega não encontrada");
            EntregaModel entregaModelReturned = optional.get();
            entregaModelReturned.setEntregador(entregadorModel);
            EntregaModel entregaModelUpdated = entregaRepository.save(entregaModelReturned);
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().put("/e-commerce/entrega/cancelamento/{id}", entregaModelUpdated.getId())
                    .then().statusCode(HttpStatus.NO_CONTENT.value());
        }

    }

    @Nested
    public class ValidacaoInicioEntregasPendentes {

        @Test
        @DisplayName("deve lancar excecao para cliente nao encontrado")
        public void deveLancarExcecaoParaClienteNaoEncontrado() throws IOException {
            StubConfig.recuperaClienteMockResponseNotFound(1L);
            given().when().get("/e-commerce/entrega/inicia-pendentes")
                    .then().statusCode(HttpStatus.NOT_FOUND.value())
                    .body("$", hasKey("message"))
                    .body("message", equalTo("not found"));
        }

        @Test
        @DisplayName("deve iniciar entregas pendentes corretamente")
        public void deveIniciarEntregasPendentesCorretamente() throws IOException {
            StubConfig.recuperaClienteMockResponseOK(1L);
            given().when().get("/e-commerce/entrega/inicia-pendentes")
                    .then().statusCode(HttpStatus.ACCEPTED.value());
        }

        @BeforeEach
        public void setup() {
            entregadorRepository.save(new EntregadorModel(null, "nome", StatusEntregador.DISPONIVEL, "teste@teste.com.br", null));
            entregadorRepository.save(new EntregadorModel(null, "nome", StatusEntregador.DISPONIVEL, "teste@teste.com.br", null));
            entregadorRepository.save(new EntregadorModel(null, "nome", StatusEntregador.DISPONIVEL, "teste@teste.com.br", null));
        }

    }


}
