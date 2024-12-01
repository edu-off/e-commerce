package br.com.ms.logistica.bdd;

import br.com.ms.logistica.application.dto.EntregadorDTO;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class DefinicaoPassosEntregador {

    private Response response;

    private EntregadorDTO entregadorResponse;

    private final String ENDPOINT_ENTREGADOR = "http://localhost:8084/e-commerce/entregador";

    @Quando("submeter um novo entregador")
    public EntregadorDTO submeterUmNovoEntregador() {
        EntregadorDTO entregadorDTO = new EntregadorDTO(null, "DISPONIVEL", "nome", "teste@teste.com.br");

        response = given().contentType(MediaType.APPLICATION_JSON_VALUE).body(entregadorDTO)
                .when().post(ENDPOINT_ENTREGADOR);

        return response.then().extract().as(EntregadorDTO.class);
    }

    @Então("o entregador é registrado com sucesso")
    public void oEntregadorÉRegistradoComSucesso() {
        response.then().statusCode(HttpStatus.CREATED.value())
                .body(matchesJsonSchemaInClasspath("./schemas/EntregadorResponseSchema.json"));
    }

    @Dado("que um entregador já foi registrado")
    public void queUmEntregadorJáFoiRegistrado() {
        entregadorResponse = submeterUmNovoEntregador();
    }

    @Quando("atualizar o entregador")
    public EntregadorDTO atualizarOEntregador() {
        EntregadorDTO entregadorDTO = new EntregadorDTO(null, "DISPONIVEL", "nome", "teste@teste.com.br");

        response = given().contentType(MediaType.APPLICATION_JSON_VALUE).body(entregadorDTO)
                .when().put(ENDPOINT_ENTREGADOR + "/{id}", entregadorResponse.getId());

        return response.then().extract().as(EntregadorDTO.class);
    }

    @Então("o entregador é atualizado com sucesso")
    public void oEntregadorÉAtualizadoComSucesso() {
        response.then().statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./schemas/EntregadorResponseSchema.json"));
    }

    @Quando("atualizar um entregador inexistente")
    public void atualizarUmEntregadorInexistente() {
        EntregadorDTO entregadorDTO = new EntregadorDTO(null, "DISPONIVEL", "nome", "teste@teste.com.br");

        response = given().contentType(MediaType.APPLICATION_JSON_VALUE).body(entregadorDTO)
                .when().put(ENDPOINT_ENTREGADOR + "/{id}", 0L);
    }

    @Então("um erro de entregador não encontrado é retornado")
    public void umErroDeEntregadorNãoEncontradoÉRetornado() {
        response.then().statusCode(HttpStatus.NOT_FOUND.value());
    }

}
