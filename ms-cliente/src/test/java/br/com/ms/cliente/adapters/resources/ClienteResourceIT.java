package br.com.ms.cliente.adapters.resources;

import br.com.ms.cliente.application.dto.ClienteDTO;
import br.com.ms.cliente.application.dto.EnderecoDTO;
import br.com.ms.cliente.domain.enums.StatusCliente;
import br.com.ms.cliente.infrastructure.models.ClienteModel;
import br.com.ms.cliente.infrastructure.models.EnderecoModel;
import br.com.ms.cliente.infrastructure.repositories.ClienteRepository;
import br.com.ms.cliente.infrastructure.repositories.EnderecoRepository;
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
import static org.hamcrest.Matchers.*;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClienteResourceIT {

    @LocalServerPort
    private int port;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;


    private ClienteModel clienteModelCreated1;
    private ClienteModel clienteModelCreated2;
    private ClienteModel clienteModelCreated3;
    private ClienteModel clienteModelCreated4;

    @BeforeEach
    public void setup() {
        RestAssured.port  = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        ClienteModel clienteModel1 = new ClienteModel(null, "teste", StatusCliente.ATIVO, "teste@teste.com.br", 1, 1L, null);
        ClienteModel clienteModel2 = new ClienteModel(null, "teste", StatusCliente.INATIVO, "teste@teste.com.br", 1, 1L, null);
        ClienteModel clienteModel3 = new ClienteModel(null, "nome", StatusCliente.INATIVO, "email@teste.com.br", 1, 1L, null);
        ClienteModel clienteModel4 = new ClienteModel(null, "nome", StatusCliente.ATIVO, "email@teste.com.br", 1, 1L, null);
        clienteModelCreated1 = clienteRepository.save(clienteModel1);
        clienteModelCreated2 = clienteRepository.save(clienteModel2);
        clienteModelCreated3 = clienteRepository.save(clienteModel3);
        clienteModelCreated4 = clienteRepository.save(clienteModel4);

        EnderecoModel enderecoModel =  new EnderecoModel(null, "logradouro", "bairro", "cidade", "SP", 1L, clienteModelCreated1);
        enderecoRepository.save(enderecoModel);
    }

    @AfterEach
    public void tearDown() {
        enderecoRepository.deleteAll();
        clienteRepository.deleteAll();
    }

    @Nested
    public class ValidacaoCriacaoCliente {

        @Test
        @DisplayName("Deve retornar unprocessable entity para criacao de cliente inválido")
        public void deveRetornarUnprocessableEntityParaCriacaoDeClienteInvalido() {
            EnderecoDTO enderecoDTO = new EnderecoDTO(null, "", "bairro", "cidade", "SP", 1L);
            ClienteDTO clienteDTO = new ClienteDTO(null, "", "ATIVO", "teste@teste.com.br", 1, 1L, enderecoDTO);
            given().contentType(MediaType.APPLICATION_JSON_VALUE).body(clienteDTO)
                    .when().post("/e-commerce/cliente")
                    .then().statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                    .body("$", hasKey("message"))
                    .body("message", equalTo("Existem dados incorretos no corpo da requisição"));
        }

        @Test
        @DisplayName("Deve retornar unprocessable entity para criacao de cliente com endereço inválido")
        public void deveRetornarUnprocessableEntityParaCriacaoDeClienteComEnderecoInvalido() {
            EnderecoDTO enderecoDTO = new EnderecoDTO(null, "", "bairro", "cidade", "SP", 1L);
            ClienteDTO clienteDTO = new ClienteDTO(null, "nome", "ATIVO", "teste@teste.com.br", 1, 1L, enderecoDTO);
            given().contentType(MediaType.APPLICATION_JSON_VALUE).body(clienteDTO)
                    .when().post("/e-commerce/cliente")
                    .then().statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                    .body("$", hasKey("message"))
                    .body("message", equalTo("Existem dados incorretos no corpo da requisição"));
        }

        @Test
        @DisplayName("Deve cadastrar cliente")
        public void deveCadastrarCliente() {
            EnderecoDTO enderecoDTO = new EnderecoDTO(null, "logradouro", "bairro", "cidade", "SP", 1L);
            ClienteDTO clienteDTO = new ClienteDTO(null, "nome", "ATIVO", "teste@teste.com.br", 1, 1L, enderecoDTO);
            given().contentType(MediaType.APPLICATION_JSON_VALUE).body(clienteDTO)
                    .when().post("/e-commerce/cliente")
                    .then().statusCode(HttpStatus.CREATED.value())
                    .body(matchesJsonSchemaInClasspath("./schemas/ClienteResponseSchema.json"));
        }

    }

    @Nested
    public class ValidacaoAtualizacaoCliente {

        @Test
        @DisplayName("Deve retornar not found para atualizacao de cliente inexistente")
        public void deveRetornarNotFoundParaAtualizacaoDeClienteInexistente() {
            EnderecoDTO enderecoDTO = new EnderecoDTO(null, "logradouro", "bairro", "cidade", "SP", 1L);
            ClienteDTO clienteDTO = new ClienteDTO(null, "nome", "ATIVO", "teste@teste.com.br", 1, 1L, enderecoDTO);
            given().contentType(MediaType.APPLICATION_JSON_VALUE).body(clienteDTO)
                    .when().put("/e-commerce/cliente/{id}", 0L)
                    .then().statusCode(HttpStatus.NOT_FOUND.value())
                    .body("$", hasKey("message"))
                    .body("message", equalTo("cliente não encontrado"));
        }

        @Test
        @DisplayName("Deve retornar unprocessable entity para atualizacao de cliente inválido")
        public void deveRetornarUnprocessableEntityParaAtualizacaoDeClienteInvalido() {
            EnderecoDTO enderecoDTO = new EnderecoDTO(null, "logradouro", "bairro", "cidade", "SP", 1L);
            ClienteDTO clienteDTO = new ClienteDTO(null, "", "ATIVO", "teste@teste.com.br", 1, 1L, enderecoDTO);
            given().contentType(MediaType.APPLICATION_JSON_VALUE).body(clienteDTO)
                    .when().put("/e-commerce/cliente/{id}", clienteModelCreated1.getId())
                    .then().statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                    .body("$", hasKey("message"))
                    .body("message", equalTo("Existem dados incorretos no corpo da requisição"));
        }

        @Test
        @DisplayName("Deve retornar unprocessable entity para atualizacao de cliente com endereço inválido")
        public void deveRetornarUnprocessableEntityParaAtualizacaoDeClienteComEnderecoInvalido() {
            EnderecoDTO enderecoDTO = new EnderecoDTO(null, "", "bairro", "cidade", "SP", 1L);
            ClienteDTO clienteDTO = new ClienteDTO(null, "nome", "ATIVO", "teste@teste.com.br", 1, 1L, enderecoDTO);
            given().contentType(MediaType.APPLICATION_JSON_VALUE).body(clienteDTO)
                    .when().put("/e-commerce/cliente/{id}", clienteModelCreated1.getId())
                    .then().statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                    .body("$", hasKey("message"))
                    .body("message", equalTo("Existem dados incorretos no corpo da requisição"));
        }


        @Test
        @DisplayName("Deve atualizar cliente")
        public void deveAtualizarCliente() {
            EnderecoDTO enderecoDTO = new EnderecoDTO(null, "logradouro", "bairro", "cidade", "SP", 1L);
            ClienteDTO clienteDTO = new ClienteDTO(null, "nome", "ATIVO", "teste@teste.com.br", 1, 1L, enderecoDTO);
            given().contentType(MediaType.APPLICATION_JSON_VALUE).body(clienteDTO)
                    .when().put("/e-commerce/cliente/{id}", clienteModelCreated1.getId())
                    .then().statusCode(HttpStatus.OK.value())
                    .body(matchesJsonSchemaInClasspath("./schemas/ClienteResponseSchema.json"));
        }

        @Test
        @DisplayName("Deve retornar not found para ativação de cliente inexistente")
        public void deveRetornarNotFoundParaAtivacaoDeClienteInexistente() {
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().put("/e-commerce/cliente/ativa/{id}", 0L)
                    .then().statusCode(HttpStatus.NOT_FOUND.value());
        }

        @Test
        @DisplayName("Deve ativar cliente")
        public void deveAtivarCliente() {
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().put("/e-commerce/cliente/ativa/{id}", clienteModelCreated1.getId())
                    .then().statusCode(HttpStatus.NO_CONTENT.value());
        }

        @Test
        @DisplayName("Deve retornar not found para ativação de cliente inexistente")
        public void deveRetornarNotFoundParaInativacaoDeClienteInexistente() {
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().put("/e-commerce/cliente/inativa/{id}", 0L)
                    .then().statusCode(HttpStatus.NOT_FOUND.value());
        }

        @Test
        @DisplayName("Deve inativar cliente")
        public void deveInativarCliente() {
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().put("/e-commerce/cliente/inativa/{id}", clienteModelCreated1.getId())
                    .then().statusCode(HttpStatus.NO_CONTENT.value());
        }

    }

    @Nested
    public class ValidacaoBuscaCliente {

        @Test
        @DisplayName("Deve buscar cliente por id")
        public void deveBuscarClientePorId() {
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().get("/e-commerce/cliente/{id}", clienteModelCreated1.getId())
                    .then().statusCode(HttpStatus.OK.value())
                    .body(matchesJsonSchemaInClasspath("./schemas/ClienteResponseSchema.json"));
        }

        @Test
        @DisplayName("Deve buscar clientes por nome")
        public void deveBuscarClientePorNome() {
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().get("/e-commerce/cliente/nome/{nome}", "nome")
                    .then().statusCode(HttpStatus.OK.value())
                    .body(matchesJsonSchemaInClasspath("./schemas/PageClienteResponseSchema.json"))
                    .body("$", hasKey("totalElements")).body("totalElements", equalTo(2));
        }

        @Test
        @DisplayName("Deve buscar clientes por e-mail")
        public void deveBuscarClientePorEmail() {
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().get("/e-commerce/cliente/email/{email}", "email")
                    .then().statusCode(HttpStatus.OK.value())
                    .body(matchesJsonSchemaInClasspath("./schemas/PageClienteResponseSchema.json"))
                    .body("$", hasKey("totalElements")).body("totalElements", equalTo(2));
        }

        @Test
        @DisplayName("Deve buscar clientes por status")
        public void deveBuscarClientePorStatus() {
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().get("/e-commerce/cliente/status/{status}", "ATIVO")
                    .then().statusCode(HttpStatus.OK.value())
                    .body(matchesJsonSchemaInClasspath("./schemas/PageClienteResponseSchema.json"))
                    .body("$", hasKey("totalElements")).body("totalElements", equalTo(2));
        }

    }

}
