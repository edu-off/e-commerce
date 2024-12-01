package br.com.ms.cliente.bdd;

import br.com.ms.cliente.application.dto.ClienteDTO;
import br.com.ms.cliente.application.dto.EnderecoDTO;
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

    private ClienteDTO clienteResponse;

    private final String ENDPOINT_CLIENTE = "http://localhost:8081/e-commerce/cliente";

    @Quando("submeter um novo cliente")
    public ClienteDTO submeterUmNovoCliente() {
        EnderecoDTO enderecoDTO = new EnderecoDTO(null, "logradouro", "bairro", "cidade", "SP", 1L);
        ClienteDTO clienteDTO = new ClienteDTO(null, "nome", "ATIVO", "teste@teste.com.br", 1, 1L, enderecoDTO);
        response = given().contentType(MediaType.APPLICATION_JSON_VALUE).body(clienteDTO)
                .when().post(ENDPOINT_CLIENTE);

        return response.then().extract().as(ClienteDTO.class);
    }

    @Então("o cliente é registrado com sucesso")
    public void oClienteÉRegistradoComSucesso() {
        response.then().statusCode(HttpStatus.CREATED.value())
                .body(matchesJsonSchemaInClasspath("./schemas/ClienteResponseSchema.json"));
    }

    @Dado("que um cliente já foi registrado")
    public void queUmClienteJáFoiRegistrado() {
        clienteResponse = submeterUmNovoCliente();
    }

    @Quando("atualizar o cliente")
    public ClienteDTO atualizarOCliente() {
        EnderecoDTO enderecoDTO = new EnderecoDTO(null, "logradouro", "bairro", "cidade", "SP", 1L);
        ClienteDTO clienteDTO = new ClienteDTO(null, "nome atualizado", "ATIVO", "teste@teste.com.br", 1, 1L, enderecoDTO);
        response = given().contentType(MediaType.APPLICATION_JSON_VALUE).body(clienteDTO)
                .when().put(ENDPOINT_CLIENTE + "/{id}", clienteResponse.getId());

        return response.then().extract().as(ClienteDTO.class);
    }

    @Então("o cliente é atualizado com sucesso")
    public void oClienteÉAtualizadoComSucesso() {
        response.then().statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./schemas/ClienteResponseSchema.json"));
    }

    @Quando("ativar o cliente")
    public void ativarOCliente() {
        response = given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().put(ENDPOINT_CLIENTE + "/ativa/{id}", clienteResponse.getId());
    }

    @Então("o cliente é ativado com sucesso")
    public void oClienteÉAtivadoComSucesso() {
        response.then().statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Quando("inativar o cliente")
    public void inativarOCliente() {
        response = given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().put(ENDPOINT_CLIENTE + "/inativa/{id}", clienteResponse.getId());
    }

    @Então("o cliente é inativado com sucesso")
    public void oClienteÉInativadoComSucesso() {
        response.then().statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Quando("solicitar a busca do cliente")
    public void solicitarABuscaDoCliente() {
        response = given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(ENDPOINT_CLIENTE + "/{id}", clienteResponse.getId());
    }

    @Então("o cliente é exibido com sucesso")
    public void oClienteÉExibidoComSucesso() {
        response.then().statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./schemas/ClienteResponseSchema.json"));
    }

    @Quando("solicitar a busca do cliente por nome")
    public void solicitarABuscaDoClientePorNome() {
        response = given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(ENDPOINT_CLIENTE + "/nome/{nome}", clienteResponse.getNome());
    }

    @Quando("solicitar a busca do cliente por e-mail")
    public void solicitarABuscaDoClientePorEMail() {
        response = given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(ENDPOINT_CLIENTE + "/email/{email}", clienteResponse.getEmail());
    }

    @Quando("solicitar a busca do cliente por status")
    public void solicitarABuscaDoClientePorStatus() {
        response = given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(ENDPOINT_CLIENTE + "/status/{status}", clienteResponse.getStatus());
    }

    @Então("os clientes são exibidos com sucesso")
    public void osClientesSãoExibidosComSucesso() {
        response.then().statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./schemas/PageClienteResponseSchema.json"));
    }

}
