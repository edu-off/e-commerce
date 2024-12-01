package br.com.ms.logistica.adapters.resources;

import br.com.ms.logistica.application.dto.EntregadorDTO;
import br.com.ms.logistica.domain.enums.StatusEntregador;
import br.com.ms.logistica.infrastructure.models.EntregadorModel;
import br.com.ms.logistica.infrastructure.repositories.EntregadorRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EntregadorResourceIT {

    @LocalServerPort
    private int port;

    @Autowired
    private EntregadorRepository entregadorRepository;

    private EntregadorModel entregadorModelCreated;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        EntregadorModel entregadorModel = new EntregadorModel(null, "nome", StatusEntregador.DISPONIVEL, "teste@teste.com.br", null);
        entregadorModelCreated = entregadorRepository.save(entregadorModel);
    }

    @AfterEach
    public void tearDown() {
        entregadorRepository.deleteAll();
    }

    @Nested
    public class ValidacaoRegistroEntregador {

        @Test
        @DisplayName("Deve retornar unprocessable entity para registro de entregador inválido")
        public void deveRetornarUnprocessableEntityParaRegistroDeEntregadorInvalido() {
            EntregadorDTO entregadorDTO = new EntregadorDTO(null, "DISPONIVEL", "", "teste@teste.com.br");
            given().contentType(MediaType.APPLICATION_JSON_VALUE).body(entregadorDTO)
                    .when().post("/e-commerce/entregador")
                    .then().statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                    .body("$", hasKey("message"))
                    .body("message", equalTo("Existem dados incorretos no corpo da requisição"));
        }

        @Test
        @DisplayName("Deve cadastrar cliente")
        public void deveCadastrarCliente() {
            EntregadorDTO entregadorDTO = new EntregadorDTO(null, "DISPONIVEL", "nome", "teste@teste.com.br");
            given().contentType(MediaType.APPLICATION_JSON_VALUE).body(entregadorDTO)
                    .when().post("/e-commerce/entregador")
                    .then().statusCode(HttpStatus.CREATED.value())
                    .body(matchesJsonSchemaInClasspath("./schemas/EntregadorResponseSchema.json"));
        }

    }

    @Nested
    public class ValidacaoAtualizacaoEntregador {

        @Test
        @DisplayName("Deve retornar not found para atualizacao de entregador inexistente")
        public void deveRetornarNotFoundParaAtualizacaoDeEntregadorInexistente() {
            EntregadorDTO entregadorDTO = new EntregadorDTO(null, "DISPONIVEL", "nome", "teste@teste.com.br");
            given().contentType(MediaType.APPLICATION_JSON_VALUE).body(entregadorDTO)
                    .when().put("/e-commerce/entregador/{id}", 0L)
                    .then().statusCode(HttpStatus.NOT_FOUND.value())
                    .body("$", hasKey("message"))
                    .body("message", equalTo("entregador não encontrado"));
        }

        @Test
        @DisplayName("Deve retornar unprocessable entity para atualizacao de entregador inválido")
        public void deveRetornarUnprocessableEntityParaAtualizacaoDeProdutoInvalido() {
            EntregadorDTO entregadorDTO = new EntregadorDTO(null, "DISPONIVEL", "", "teste@teste.com.br");
            given().contentType(MediaType.APPLICATION_JSON_VALUE).body(entregadorDTO)
                    .when().put("/e-commerce/entregador/{id}", entregadorModelCreated.getId())
                    .then().statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                    .body("$", hasKey("message"))
                    .body("message", equalTo("Existem dados incorretos no corpo da requisição"));
        }

        @Test
        @DisplayName("Deve atualizar produto")
        public void deveAtualizarProduto() {
            EntregadorDTO entregadorDTO = new EntregadorDTO(null, "DISPONIVEL", "nome", "teste@teste.com.br");
            given().contentType(MediaType.APPLICATION_JSON_VALUE).body(entregadorDTO)
                    .when().put("/e-commerce/entregador/{id}", entregadorModelCreated.getId())
                    .then().statusCode(HttpStatus.OK.value())
                    .body(matchesJsonSchemaInClasspath("./schemas/EntregadorResponseSchema.json"));
        }

    }

}
