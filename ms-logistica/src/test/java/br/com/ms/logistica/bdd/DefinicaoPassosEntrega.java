package br.com.ms.logistica.bdd;

import br.com.ms.logistica.application.dto.ClienteDTO;
import br.com.ms.logistica.application.dto.EnderecoDTO;
import br.com.ms.logistica.application.dto.EntregaDTO;
import br.com.ms.logistica.utils.CreatePedidoDTO;
import br.com.ms.logistica.utils.CreateProdutoDTO;
import br.com.ms.logistica.utils.CreateProdutosPedidoDTO;
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

public class DefinicaoPassosEntrega {

    private Response response;
    private EntregaDTO entregaResponse;
    private CreatePedidoDTO pedidoResponse;
    private ClienteDTO clienteResponse;
    private CreateProdutoDTO produtoResponse;

    private final String ENDPOINT_CLIENTE = "http://localhost:8081/e-commerce/cliente";
    private final String ENDPOINT_PRODUTO = "http://localhost:8082/e-commerce/produto";
    private final String ENDPOINT_PEDIDO = "http://localhost:8083/e-commerce/pedido";
    private final String ENDPOINT_ENTREGA = "http://localhost:8084/e-commerce/entrega";

    @Quando("um cliente é registrado")
    public void umClienteÉRegistrado() {
        EnderecoDTO enderecoDTO = new EnderecoDTO(null, "logradouro", "bairro", "cidade", "SP", 1L);
        ClienteDTO clienteDTO = new ClienteDTO(null, "nome", "ATIVO", "teste@teste.com.br", 1, 1L, enderecoDTO);
        response = given().contentType(MediaType.APPLICATION_JSON_VALUE).body(clienteDTO)
                .when().post(ENDPOINT_CLIENTE);

        clienteResponse = response.then().extract().as(ClienteDTO.class);
    }

    @E("um produto é registrado")
    public void umProdutoÉRegistrado() {
        CreateProdutoDTO produtoDTO = new CreateProdutoDTO(null, "nome", "descricao", 10.99, 2000, "categoria");
        response = given().contentType(MediaType.APPLICATION_JSON_VALUE).body(produtoDTO)
                .when().post(ENDPOINT_PRODUTO);

        produtoResponse = response.then().extract().as(CreateProdutoDTO.class);
    }

    @E("um pedido for criado")
    public void umPedidoForCriado() {
        List<CreateProdutosPedidoDTO> produtos = List.of(
                new CreateProdutosPedidoDTO(produtoResponse.getId(), 1, 20.00));
        CreatePedidoDTO pedidoDTO = new CreatePedidoDTO(null, clienteResponse.getId(), "EM_ABERTO", produtos);
        response = given().contentType(MediaType.APPLICATION_JSON_VALUE).body(pedidoDTO)
                .when().post(ENDPOINT_PEDIDO);

        pedidoResponse = response.then().extract().as(CreatePedidoDTO.class);
    }

    @E("o mesmo pedido for confirmado")
    public void oMesmoPedidoForConfirmado() {
        given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().put(ENDPOINT_PEDIDO + "/confirmacao/{id}", pedidoResponse.getId());
    }

    @E("submeter uma nova entrega")
    public EntregaDTO submeterUmaNovaEntrega() {
        EntregaDTO entregaDTO = new EntregaDTO(null, "PENDENTE", clienteResponse.getId(), pedidoResponse.getId());

        response = given().contentType(MediaType.APPLICATION_JSON_VALUE).body(entregaDTO)
                .when().post(ENDPOINT_ENTREGA);

        String body = response.getBody().asString();

        EntregaDTO entregaDTOCreated = response.then().extract().as(EntregaDTO.class);
        entregaDTOCreated.setId(entregaDTOCreated.getId() - 1);
        return entregaDTOCreated;
    }

    @Então("a entrega é registrada com sucesso")
    public void aEntregaÉRegistradaComSucesso() {
        response.then().statusCode(HttpStatus.CREATED.value())
                .body(matchesJsonSchemaInClasspath("./schemas/EntregaResponseSchema.json"));
    }

    @Dado("que uma entrega já foi registrada")
    public void queUmaEntregaJáFoiRegistrada() {
        umClienteÉRegistrado();
        umProdutoÉRegistrado();
        umPedidoForCriado();
        oMesmoPedidoForConfirmado();
        entregaResponse = submeterUmaNovaEntrega();
    }

    @Quando("cancelar a entrega existente")
    public void cancelarAEntregaExistente() {
        response = given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().put(ENDPOINT_ENTREGA + "/cancelamento/{id}", entregaResponse.getId());
    }

    @Então("a entrega é cancelada com sucesso")
    public void aEntregaÉCanceladaComSucesso() {
        response.then().statusCode(HttpStatus.NO_CONTENT.value());
    }

}
