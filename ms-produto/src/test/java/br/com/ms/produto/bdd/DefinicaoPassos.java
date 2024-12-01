package br.com.ms.produto.bdd;

import br.com.ms.produto.application.dto.ProdutoDTO;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class DefinicaoPassos {

    private Response response;

    private ProdutoDTO produtoResponse;

    private final String ENDPOINT_PRODUTO = "http://localhost:8082/e-commerce/produto";

    @Quando("submeter um novo produto")
    public ProdutoDTO submeterUmNovoProduto() {
        ProdutoDTO produtoDTO = new ProdutoDTO(null, "nome", "descricao", 1.1, 1, "categoria");
        response = given().contentType(MediaType.APPLICATION_JSON_VALUE).body(produtoDTO)
                .when().post(ENDPOINT_PRODUTO);

        return response.then().extract().as(ProdutoDTO.class);
    }

    @Então("o produto é registrado com sucesso")
    public void oProdutoÉRegistradoComSucesso() {
        response.then().statusCode(HttpStatus.CREATED.value())
                .body(matchesJsonSchemaInClasspath("./schemas/ProdutoResponseSchema.json"));
    }

    @Dado("que um produto já foi registrado")
    public void queUmProdutoJáFoiRegistrado() {
        produtoResponse = submeterUmNovoProduto();
    }

    @Quando("atualizar o produto")
    public ProdutoDTO atualizarOProduto() {
        ProdutoDTO produtoDTO = new ProdutoDTO(null, "nome", "descricao", 1.1, 1, "categoria");
        response = given().contentType(MediaType.APPLICATION_JSON_VALUE).body(produtoDTO)
                .when().put(ENDPOINT_PRODUTO + "/{id}", produtoResponse.getId());

        return response.then().extract().as(ProdutoDTO.class);
    }

    @Então("o produto é atualizado com sucesso")
    public void oProdutoÉAtualizadoComSucesso() {
        response.then().statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./schemas/ProdutoResponseSchema.json"));
    }

    @Quando("incrementar o estoque do produto")
    public ProdutoDTO incrementarOEstoqueDoProduto() {
        ProdutoDTO produtoDTO = new ProdutoDTO(null, "nome", "descricao", 1.1, 1, "categoria");
        response = given().contentType(MediaType.APPLICATION_JSON_VALUE).body(produtoDTO)
                .when().put(ENDPOINT_PRODUTO + "/{id}/estoque/adiciona/{quantidade}", produtoResponse.getId(), 1);

        return response.then().extract().as(ProdutoDTO.class);
    }

    @Então("o estoque do produto é incrementado com sucesso")
    public void oEstoqueDoProdutoÉIncrementadoComSucesso() {
        response.then().statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./schemas/ProdutoResponseSchema.json"));
    }

    @Quando("decrementar o estoque do produto")
    public ProdutoDTO decrementarOEstoqueDoProduto() {
        ProdutoDTO produtoDTO = new ProdutoDTO(null, "nome", "descricao", 1.1, 1, "categoria");
        response = given().contentType(MediaType.APPLICATION_JSON_VALUE).body(produtoDTO)
                .when().put(ENDPOINT_PRODUTO + "/{id}/estoque/remove/{quantidade}", produtoResponse.getId(), 1);

        return response.then().extract().as(ProdutoDTO.class);
    }

    @Então("o estoque do produto é decrementado com sucesso")
    public void oEstoqueDoProdutoÉDecrementadoComSucesso() {
        response.then().statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./schemas/ProdutoResponseSchema.json"));
    }

    @Quando("remover o produto")
    public void removerOProduto() {
        response = given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete(ENDPOINT_PRODUTO + "/{id}", produtoResponse.getId());
    }

    @Então("o produto é removido com sucesso")
    public void oProdutoÉRemovidoComSucesso() {
        response.then().statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Quando("solicitar a busca do produto")
    public ProdutoDTO solicitarABuscaDoProduto() {
        response = given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(ENDPOINT_PRODUTO + "/{id}", produtoResponse.getId());

        return response.then().extract().as(ProdutoDTO.class);
    }

    @Então("o produto é exibido com sucesso")
    public void oProdutoÉExibidoComSucesso() {
        response.then().statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./schemas/ProdutoResponseSchema.json"));
    }

    @Quando("solicitar a busca do produto por nome")
    public void solicitarABuscaDoProdutoPorNome() {
        response = given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(ENDPOINT_PRODUTO + "/nome/{nome}", "nome");
    }

    @Quando("solicitar a busca do produto por descrição")
    public void solicitarABuscaDoProdutoPorDescrição() {
        response = given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(ENDPOINT_PRODUTO + "/descricao/{descricao}", "descricao");
    }

    @Quando("solicitar a busca do produto por categoria")
    public void solicitarABuscaDoProdutoPorCategoria() {
        response = given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(ENDPOINT_PRODUTO + "/categoria/{categoria}", "categoria");
    }

    @Então("os produtos são exibidos com sucesso")
    public void osProdutosSãoExibidosComSucesso() {
        response.then().statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./schemas/PageProdutoResponseSchema.json"));
    }

}
