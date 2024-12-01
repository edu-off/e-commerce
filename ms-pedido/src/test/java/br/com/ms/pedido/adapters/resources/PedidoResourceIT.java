package br.com.ms.pedido.adapters.resources;

import br.com.ms.pedido.application.dto.PedidoDTO;
import br.com.ms.pedido.application.dto.ProdutoDTO;
import br.com.ms.pedido.config.StubConfig;
import br.com.ms.pedido.domain.enums.StatusPedido;
import br.com.ms.pedido.infrastructure.models.PedidoModel;
import br.com.ms.pedido.infrastructure.repositories.PedidoRepository;
import io.restassured.RestAssured;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
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
public class PedidoResourceIT {

    @LocalServerPort
    private int port;

    @Autowired
    private PedidoRepository pedidoRepository;

    private PedidoModel pedidoModelSetup;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        List<Map<String, Object>> produtos = List.of(Map.of("id", 1L, "quantidade", 1),
                Map.of("id", 2L, "quantidade", 2));
        PedidoModel pedidoModel = new PedidoModel(null, 1L, StatusPedido.EM_ABERTO, null, produtos, LocalDateTime.now(), null, null, null);
        pedidoModelSetup = pedidoRepository.save(pedidoModel);
    }

    @AfterEach
    public void tearDown() {
        pedidoRepository.deleteAll();
    }

    @Nested
    public class RegistrandoPedido {

        @Test
        @DisplayName("Deve retornar unprocessable entity para registro de Entrega inválido")
        public void deveRetornarUnprocessableEntityParaRegistroDeEntregaInvalido() {
            List<ProdutoDTO> produtos = List.of(new ProdutoDTO(1L, 1, 0.0), new ProdutoDTO(2L, 2, 0.0));
            PedidoDTO pedidoDTO = new PedidoDTO(null, 1L, "", produtos);
            given().contentType(MediaType.APPLICATION_JSON_VALUE).body(pedidoDTO)
                    .when().post("/e-commerce/pedido")
                    .then().statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                    .body("$", hasKey("message"))
                    .body("message", equalTo("Existem dados incorretos no corpo da requisição"));
        }

        @Test
        @DisplayName("Deve registrar pedido")
        public void deveRegistrarPedido() throws IOException {
            StubConfig.recuperaClienteMockResponseOK(1L);
            StubConfig.recuperaProdutoMockResponseOK(1L, 1, 0.0);
            StubConfig.recuperaProdutoMockResponseOK(2L, 2, 0.0);
            List<ProdutoDTO> produtos = List.of(new ProdutoDTO(1L, 1, 0.0), new ProdutoDTO(2L, 2, 0.0));
            PedidoDTO pedidoDTO = new PedidoDTO(null, 1L, "EM_ABERTO", produtos);
            given().contentType(MediaType.APPLICATION_JSON_VALUE).body(pedidoDTO)
                    .when().post("/e-commerce/pedido")
                    .then().statusCode(HttpStatus.CREATED.value())
                    .body(matchesJsonSchemaInClasspath("./schemas/PedidoResponseSchema.json"));
        }

    }

    @Nested
    public class AtualizandoListaProdutos {

        @Test
        @DisplayName("Deve retornar not found para atualizacao de produtos de pedido inexistente")
        public void deveRetornarNotFoundParaAtualizacaoDeProdutosDePedidoInexistente() {
            List<ProdutoDTO> produtos = List.of(new ProdutoDTO(1L, 1, 0.0), new ProdutoDTO(2L, 2, 0.0));
            given().contentType(MediaType.APPLICATION_JSON_VALUE).body(produtos)
                    .when().put("/e-commerce/pedido/{id}/produtos", 0L)
                    .then().statusCode(HttpStatus.NOT_FOUND.value());
        }

        @Test
        @DisplayName("Deve atualizar produtos de pedido corretamente")
        public void deveAtualizarProdutosDePedidoCorretamente() {
            List<ProdutoDTO> produtos = List.of(new ProdutoDTO(1L, 1, 0.0), new ProdutoDTO(2L, 2, 0.0));
            given().contentType(MediaType.APPLICATION_JSON_VALUE).body(produtos)
                    .when().put("/e-commerce/pedido/{id}/produtos", pedidoModelSetup.getId())
                    .then().statusCode(HttpStatus.OK.value())
                    .body(matchesJsonSchemaInClasspath("./schemas/PedidoResponseSchema.json"));
        }

    }

    @Nested
    public class ConfirmandoPedido {

        @Test
        @DisplayName("Deve retornar not found para confirmacao de pedido inexistente")
        public void deveRetornarNotFoundParaConfirmacaoDePedidoInexistente() {
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().put("/e-commerce/pedido/confirmacao/{id}", 0L)
                    .then().statusCode(HttpStatus.NOT_FOUND.value());
        }

        @Test
        @DisplayName("Deve confirmar pedido corretamente")
        public void deveConfirmarPedidoCorretamente() throws IOException {
            StubConfig.recuperaProdutoMockResponseOK(1L, 10000, 0.0);
            StubConfig.recuperaProdutoMockResponseOK(2L, 10000, 0.0);
            StubConfig.registraEntregaMockResponseOK(1L, pedidoModelSetup.getId());
            StubConfig.decrementaEstoqueMockResponseOK(1L, 1);
            StubConfig.decrementaEstoqueMockResponseOK(2L, 2);
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().put("/e-commerce/pedido/confirmacao/{id}", pedidoModelSetup.getId())
                    .then().statusCode(HttpStatus.NO_CONTENT.value());
        }

    }

    @Nested
    public class CancelandoPedido {

        @Test
        @DisplayName("Deve retornar not found para cancelamento de pedido inexistente")
        public void deveRetornarNotFoundParaCancelamentoDePedidoInexistente() {
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().put("/e-commerce/pedido/cancelamento/{id}", 0L)
                    .then().statusCode(HttpStatus.NOT_FOUND.value());
        }

        @Test
        @DisplayName("Deve cancelar pedido corretamente")
        public void deveCancelarPedidoCorretamente() throws IOException {
            StubConfig.incrementaEstoqueMockResponseOK(1L, 1);
            StubConfig.incrementaEstoqueMockResponseOK(2L, 2);
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().put("/e-commerce/pedido/cancelamento/{id}", pedidoModelSetup.getId())
                    .then().statusCode(HttpStatus.NO_CONTENT.value());
        }

    }

    @Nested
    public class ConcluindoPedido {

        @Test
        @DisplayName("Deve retornar not found para conclusao de pedido inexistente")
        public void deveRetornarNotFoundParaCancelamentoDePedidoInexistente() {
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().put("/e-commerce/pedido/conclusao/{id}", 0L)
                    .then().statusCode(HttpStatus.NOT_FOUND.value());
        }

        @Test
        @DisplayName("Deve concluir pedido corretamente")
        public void deveConcluirPedidoCorretamente() {
            Optional<PedidoModel> optional = pedidoRepository.findById(pedidoModelSetup.getId());
            if (optional.isEmpty())
                throw new NoSuchElementException("pedido não encontrado");
            PedidoModel pedidoModel = optional.get();
            pedidoModel.setStatus(StatusPedido.CONFIRMADO);
            pedidoRepository.save(pedidoModel);
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().put("/e-commerce/pedido/conclusao/{id}", pedidoModelSetup.getId())
                    .then().statusCode(HttpStatus.NO_CONTENT.value());
        }

    }

    @Nested
    public class BuscandoPedido {

        @Test
        @DisplayName("deve lancar excecao para pedido nao encontrado")
        public void deveLancarExcecaoParaPedidoNaoEncontrado() {
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().get("/e-commerce/pedido/{id}", 0L)
                    .then().statusCode(HttpStatus.NOT_FOUND.value());
        }

        @Test
        @DisplayName("deve buscar pedido corretamente")
        public void deveBuscarPedidoCorretamente() {
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().get("/e-commerce/pedido/{id}", pedidoModelSetup.getId())
                    .then().statusCode(HttpStatus.OK.value())
                    .body(matchesJsonSchemaInClasspath("./schemas/PedidoResponseSchema.json"));

        }

    }

}
