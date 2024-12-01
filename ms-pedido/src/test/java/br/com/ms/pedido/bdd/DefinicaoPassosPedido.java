package br.com.ms.pedido.bdd;

import br.com.ms.pedido.application.dto.PedidoDTO;
import br.com.ms.pedido.application.dto.ProdutoDTO;
import br.com.ms.pedido.utils.CreateClienteDTO;
import br.com.ms.pedido.utils.CreateEnderecoDTO;
import br.com.ms.pedido.utils.CreateProdutoDTO;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class DefinicaoPassosPedido {

    private Response response;
    private PedidoDTO pedidoResponse;
    private CreateClienteDTO clienteResponse;
    private CreateProdutoDTO produtoResponse1;
    private CreateProdutoDTO produtoResponse2;

    private final String ENDPOINT_CLIENTE = "http://localhost:8081/e-commerce/cliente";
    private final String ENDPOINT_PRODUTO = "http://localhost:8082/e-commerce/produto";
    private final String ENDPOINT_PEDIDO = "http://localhost:8083/e-commerce/pedido";

    @Quando("um cliente é registrado")
    public void umClienteÉRegistrado() {
        CreateEnderecoDTO endereco = new CreateEnderecoDTO(null, "logradouro", "bairro", "cidade", "SP", 1L);
        CreateClienteDTO cliente = new CreateClienteDTO(null, "nome", "ATIVO", "teste@teste.com.br", 1, 1L, endereco);
        response = given().contentType(MediaType.APPLICATION_JSON_VALUE).body(cliente)
                .when().post(ENDPOINT_CLIENTE);

        clienteResponse = response.then().extract().as(CreateClienteDTO.class);
    }

    @E("produto são registrados")
    public void produtoSãoRegistrados() {
        CreateProdutoDTO produto = new CreateProdutoDTO(null, "nome", "descricao", 10.99, 2000, "categoria");
        response = given().contentType(MediaType.APPLICATION_JSON_VALUE).body(produto)
                .when().post(ENDPOINT_PRODUTO);

        produtoResponse1 = response.then().extract().as(CreateProdutoDTO.class);

        response = given().contentType(MediaType.APPLICATION_JSON_VALUE).body(produto)
                .when().post(ENDPOINT_PRODUTO);

        produtoResponse2 = response.then().extract().as(CreateProdutoDTO.class);
    }

    @E("um novo pedido é submetido")
    public PedidoDTO  umNovoPedidoÉSubmetido() {
        List<ProdutoDTO> produtos = List.of(new ProdutoDTO(produtoResponse1.getId(), 1, 0.0));
        PedidoDTO pedidoDTO = new PedidoDTO(null, clienteResponse.getId(), "EM_ABERTO", produtos);

        response = given().contentType(MediaType.APPLICATION_JSON_VALUE).body(pedidoDTO)
                .when().post(ENDPOINT_PEDIDO);

        return response.then().extract().as(PedidoDTO.class);
    }

    @Então("o pedido é registrado com sucesso")
    public void oPedidoÉRegistradoComSucesso() {
        response.then().statusCode(HttpStatus.CREATED.value())
                .body(matchesJsonSchemaInClasspath("./schemas/PedidoResponseSchema.json"));
    }

    @Dado("que um pedido já foi registrado")
    public void queUmPedidoJáFoiRegistrado() {
        umClienteÉRegistrado();
        produtoSãoRegistrados();
        pedidoResponse = umNovoPedidoÉSubmetido();
    }

    @Quando("atualizar a lista de produtos de um produto")
    public void atualizarAListaDeProdutosDeUmProduto() {
        List<ProdutoDTO> produtos = List.of(
                new ProdutoDTO(produtoResponse1.getId(), 1, 0.0),
                new ProdutoDTO(produtoResponse2.getId(), 2, 0.0));
        response = given().contentType(MediaType.APPLICATION_JSON_VALUE).body(produtos)
                .when().put(ENDPOINT_PEDIDO + "/{id}/produtos", pedidoResponse.getId());
    }

    @Então("a lista de produtos do pedido é atualizada com sucesso")
    public void aListaDeProdutosDoPedidoÉAtualizadaComSucesso() {
        response.then().statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./schemas/PedidoResponseSchema.json"));
    }

    @Quando("cancelar o pedido existente")
    public void cancelarOPedidoExistente() {
        response = given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().put(ENDPOINT_PEDIDO + "/cancelamento/{id}", pedidoResponse.getId());
    }

    @Então("o pedido é cancelado com sucesso")
    public void oPedidoÉCanceladoComSucesso() {
        response.then().statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Quando("confirmar o pedido existente")
    public void confirmarOPedidoExistente() {
        response = given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().put(ENDPOINT_PEDIDO + "/confirmacao/{id}", pedidoResponse.getId());
    }

    @Então("o pedido é confirmado com sucesso")
    public void oPedidoÉConfirmadoComSucesso() {
        response.then().statusCode(HttpStatus.NO_CONTENT.value());
    }

    @E("que um pedido já foi confirmado")
    public void queUmPedidoJáFoiConfirmado() {
        given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().put(ENDPOINT_PEDIDO + "/confirmacao/{id}", pedidoResponse.getId());
    }

    @Quando("concluir o pedido existente")
    public void concluirOPedidoExistente() {
        response = given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().put(ENDPOINT_PEDIDO + "/conclusao/{id}", pedidoResponse.getId());
    }

    @Então("o pedido é concluido com sucesso")
    public void oPedidoÉConcluidoComSucesso() {
        response.then().statusCode(HttpStatus.NO_CONTENT.value());
    }

}
