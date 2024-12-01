package br.com.ms.cliente.adapters.resources;

import br.com.ms.cliente.adapters.controllers.AtualizacaoClienteController;
import br.com.ms.cliente.adapters.controllers.AtualizacaoStatusClienteController;
import br.com.ms.cliente.adapters.controllers.BuscaClienteController;
import br.com.ms.cliente.adapters.controllers.CadastroClienteController;
import br.com.ms.cliente.application.dto.ClienteDTO;
import br.com.ms.cliente.domain.enums.StatusCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/cliente")
public class ClienteResource {

    private final CadastroClienteController cadastroCliente;
    private final AtualizacaoClienteController atualizacaoCliente;
    private final AtualizacaoStatusClienteController atualizacaoStatusCliente;
    private final BuscaClienteController buscaCliente;

    @Autowired
    public ClienteResource(CadastroClienteController cadastroCliente,
                           AtualizacaoClienteController atualizacaoCliente,
                           AtualizacaoStatusClienteController atualizacaoStatusCliente,
                           BuscaClienteController buscaCliente) {
        this.cadastroCliente = cadastroCliente;
        this.atualizacaoCliente = atualizacaoCliente;
        this.atualizacaoStatusCliente = atualizacaoStatusCliente;
        this.buscaCliente = buscaCliente;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClienteDTO> cadastra(@RequestBody @Validated ClienteDTO requestClienteDTO) {
        ClienteDTO responseClienteDTO = cadastroCliente.execute(requestClienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseClienteDTO);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClienteDTO> atualiza(@PathVariable Long id, @RequestBody @Validated ClienteDTO requestClienteDTO) {
        ClienteDTO responseClienteDTO = atualizacaoCliente.execute(id, requestClienteDTO);
        return ResponseEntity.ok(responseClienteDTO);
    }

    @PutMapping(value = "/inativa/{id}")
    public ResponseEntity<String> inativa(@PathVariable Long id) {
        atualizacaoStatusCliente.execute(id, StatusCliente.INATIVO);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/ativa/{id}")
    public ResponseEntity<String> ativa(@PathVariable Long id) {
        atualizacaoStatusCliente.execute(id, StatusCliente.ATIVO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClienteDTO> buscaPorId(@PathVariable Long id) {
        ClienteDTO clienteDTO = buscaCliente.porId(id);
        return ResponseEntity.ok(clienteDTO);
    }

    @GetMapping(value = "/email/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ClienteDTO>> buscaPorEmail(@PathVariable String email, Pageable pageable) {
        Page<ClienteDTO> clientes = buscaCliente.porEmail(email, pageable);
        return ResponseEntity.ok(clientes);
    }

    @GetMapping(value = "/nome/{nome}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ClienteDTO>> buscaPorNome(@PathVariable String nome, Pageable pageable) {
        Page<ClienteDTO> clientes = buscaCliente.porNome(nome, pageable);
        return ResponseEntity.ok(clientes);
    }

    @GetMapping(value = "/status/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ClienteDTO>> buscaPorStatus(@PathVariable String status, Pageable pageable) {
        Page<ClienteDTO> clientes = buscaCliente.porStatus(status, pageable);
        return ResponseEntity.ok(clientes);
    }

}
