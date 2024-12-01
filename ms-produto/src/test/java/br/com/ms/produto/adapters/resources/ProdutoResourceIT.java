package br.com.ms.produto.adapters.resources;

import br.com.ms.produto.application.dto.ProdutoDTO;
import br.com.ms.produto.infrastructure.models.ProdutoModel;
import br.com.ms.produto.infrastructure.repositories.ProdutoRepository;
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
public class ProdutoResourceIT {

    @LocalServerPort
    private int port;

    @Autowired
    private ProdutoRepository produtoRepository;

    private ProdutoModel produtoModelCreated1;
    private ProdutoModel produtoModelCreated2;
    private ProdutoModel produtoModelCreated3;
    private ProdutoModel produtoModelCreated4;

    @BeforeEach
    public void setup() {
        RestAssured.port  = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        ProdutoModel produtoModel1 = new ProdutoModel(null, "teste", "descricao", 1.1, 1, "categoria");
        ProdutoModel produtoModel2 = new ProdutoModel(null, "teste", "descricao", 1.1, 1, "categoria");
        ProdutoModel produtoModel3 = new ProdutoModel(null, "nome", "teste", 1.1, 1, "teste");
        ProdutoModel produtoModel4 = new ProdutoModel(null, "nome", "teste", 1.1, 1, "teste");
        produtoModelCreated1 = produtoRepository.save(produtoModel1);
        produtoModelCreated2 = produtoRepository.save(produtoModel2);
        produtoModelCreated3 = produtoRepository.save(produtoModel3);
        produtoModelCreated4 = produtoRepository.save(produtoModel4);
    }

    @AfterEach
    public void tearDown() {
        produtoRepository.deleteAll();
    }

    @Nested
    public class ValidacaoRegistroProduto {

        @Test
        @DisplayName("Deve retornar unprocessable entity para registro de produto inválido")
        public void deveRetornarUnprocessableEntityParaRegistroDeProdutoInvalido() {
            ProdutoDTO produtoDTO = new ProdutoDTO(null, "", "descricao", 1.1, 1, "categoria");
            given().contentType(MediaType.APPLICATION_JSON_VALUE).body(produtoDTO)
                    .when().post("/e-commerce/produto")
                    .then().statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                    .body("$", hasKey("message"))
                    .body("message", equalTo("Existem dados incorretos no corpo da requisição"));
        }

        @Test
        @DisplayName("Deve cadastrar cliente")
        public void deveCadastrarCliente() {
            ProdutoDTO produtoDTO = new ProdutoDTO(null, "nome", "descricao", 1.1, 1, "categoria");
            given().contentType(MediaType.APPLICATION_JSON_VALUE).body(produtoDTO)
                    .when().post("/e-commerce/produto")
                    .then().statusCode(HttpStatus.CREATED.value())
                    .body(matchesJsonSchemaInClasspath("./schemas/ProdutoResponseSchema.json"));
        }

    }

    @Nested
    public class ValidacaoAtualizacaoProduto {

        @Test
        @DisplayName("Deve retornar not found para atualizacao de produto inexistente")
        public void deveRetornarNotFoundParaAtualizacaoDeProdutoInexistente() {
            ProdutoDTO produtoDTO = new ProdutoDTO(null, "nome", "descricao", 1.1, 1, "categoria");
            given().contentType(MediaType.APPLICATION_JSON_VALUE).body(produtoDTO)
                    .when().put("/e-commerce/produto/{id}", 0L)
                    .then().statusCode(HttpStatus.NOT_FOUND.value())
                    .body("$", hasKey("message"))
                    .body("message", equalTo("produto não encontrado"));
        }

        @Test
        @DisplayName("Deve retornar unprocessable entity para atualizacao de produto inválido")
        public void deveRetornarUnprocessableEntityParaAtualizacaoDeProdutoInvalido() {
            ProdutoDTO produtoDTO = new ProdutoDTO(null, "", "descricao", 1.1, 1, "categoria");
            given().contentType(MediaType.APPLICATION_JSON_VALUE).body(produtoDTO)
                    .when().put("/e-commerce/produto/{id}", produtoModelCreated1.getId())
                    .then().statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                    .body("$", hasKey("message"))
                    .body("message", equalTo("Existem dados incorretos no corpo da requisição"));
        }

        @Test
        @DisplayName("Deve atualizar produto")
        public void deveAtualizarProduto() {
            ProdutoDTO produtoDTO = new ProdutoDTO(null, "nome atualizado", "descricao", 1.1, 1, "categoria");
            given().contentType(MediaType.APPLICATION_JSON_VALUE).body(produtoDTO)
                    .when().put("/e-commerce/produto/{id}", produtoModelCreated1.getId())
                    .then().statusCode(HttpStatus.OK.value())
                    .body(matchesJsonSchemaInClasspath("./schemas/ProdutoResponseSchema.json"));
        }

        @Test
        @DisplayName("Deve retornar not found para incrementar estoque de produto inexistente")
        public void deveRetornarNotFoundParaIncrementarEstoqueDeProdutoInexistente() {
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().put("/e-commerce/produto/{id}/estoque/adiciona/{quantidade}", 0L, 1)
                    .then().statusCode(HttpStatus.NOT_FOUND.value())
                    .body("$", hasKey("message"))
                    .body("message", equalTo("produto não encontrado"));
        }

        @Test
        @DisplayName("Deve incrementar estoque")
        public void deveIncrementarEstoque() {
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().put("/e-commerce/produto/{id}/estoque/adiciona/{quantidade}", produtoModelCreated1.getId(), 1)
                    .then().statusCode(HttpStatus.OK.value())
                    .body(matchesJsonSchemaInClasspath("./schemas/ProdutoResponseSchema.json"));
        }

        @Test
        @DisplayName("Deve retornar not found para decrementar estoque de produto inexistente")
        public void deveRetornarNotFoundParaDecrementarEstoqueDeProdutoInexistente() {
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().put("/e-commerce/produto/{id}/estoque/remove/{quantidade}", 0L, 1)
                    .then().statusCode(HttpStatus.NOT_FOUND.value())
                    .body("$", hasKey("message"))
                    .body("message", equalTo("produto não encontrado"));
        }

        @Test
        @DisplayName("Deve retornar bad request para quantidade de decremento superior ao existente")
        public void deveRetornarBadRequestParaQuantidadeDeDecrementoSuperiorAoExistente() {
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().put("/e-commerce/produto/{id}/estoque/remove/{quantidade}", produtoModelCreated1.getId(), 10)
                    .then().statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("$", hasKey("message"))
                    .body("message", equalTo("quantidade a ser subtraida é superior a quantidade existente"));
        }


        @Test
        @DisplayName("Deve decrementar estoque")
        public void deveDecrementarEstoque() {
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().put("/e-commerce/produto/{id}/estoque/remove/{quantidade}", produtoModelCreated1.getId(), 1)
                    .then().statusCode(HttpStatus.OK.value())
                    .body(matchesJsonSchemaInClasspath("./schemas/ProdutoResponseSchema.json"));
        }

    }

    @Nested
    public class ValidacaoRemocaoProduto {

        @Test
        @DisplayName("Deve retornar not found para remocao de produto inexistente")
        public void deveRetornarNotFoundParaRemocaoDeProdutoInexistente() {
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().delete("/e-commerce/produto/{id}", 0L)
                    .then().statusCode(HttpStatus.NOT_FOUND.value())
                    .body("$", hasKey("message"))
                    .body("message", equalTo("produto não encontrado"));
        }

        @Test
        @DisplayName("Deve remover produto")
        public void deveRemoverProduto() {
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().delete("/e-commerce/produto/{id}", produtoModelCreated1.getId())
                    .then().statusCode(HttpStatus.NO_CONTENT.value());
            }

    }

    @Nested
    public class ValidacaoBuscaProduto {

        @Test
        @DisplayName("Deve buscar produto por id")
        public void deveBuscarProdutoPorId() {
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().get("/e-commerce/produto/{id}", produtoModelCreated1.getId())
                    .then().statusCode(HttpStatus.OK.value())
                    .body(matchesJsonSchemaInClasspath("./schemas/ProdutoResponseSchema.json"));
        }

        @Test
        @DisplayName("Deve buscar produtos por nome")
        public void deveBuscarProdutosPorNome() {
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().get("/e-commerce/produto/nome/{nome}", "nome")
                    .then().statusCode(HttpStatus.OK.value())
                    .body(matchesJsonSchemaInClasspath("schemas/PageProdutoResponseSchema.json"))
                    .body("$", hasKey("totalElements")).body("totalElements", equalTo(2));
        }

        @Test
        @DisplayName("Deve buscar produtos por descricao")
        public void deveBuscarProdutosPorDescricao() {
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().get("/e-commerce/produto/descricao/{descricao}", "descricao")
                    .then().statusCode(HttpStatus.OK.value())
                    .body(matchesJsonSchemaInClasspath("schemas/PageProdutoResponseSchema.json"))
                    .body("$", hasKey("totalElements")).body("totalElements", equalTo(2));
        }

        @Test
        @DisplayName("Deve buscar produtos por categoria")
        public void deveBuscarProdutosPorCategoria() {
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().get("/e-commerce/produto/categoria/{categoria}", "categoria")
                    .then().statusCode(HttpStatus.OK.value())
                    .body(matchesJsonSchemaInClasspath("schemas/PageProdutoResponseSchema.json"))
                    .body("$", hasKey("totalElements")).body("totalElements", equalTo(2));
        }

    }



}
