package br.com.ms.logistica.adapters.resources;

import br.com.ms.logistica.adapters.controllers.CancelaEntregaController;
import br.com.ms.logistica.adapters.controllers.ConcluiEntregaController;
import br.com.ms.logistica.adapters.controllers.IniciaEntregaController;
import br.com.ms.logistica.adapters.controllers.RegistraEntregaController;
import br.com.ms.logistica.application.dto.EntregaDTO;
import br.com.ms.logistica.application.usecases.IniciaEntrega;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/entrega")
public class EntregaResource {

    private final RegistraEntregaController registraEntrega;
    private final ConcluiEntregaController concluiEntrega;
    private final CancelaEntregaController cancelaEntrega;
    private final IniciaEntregaController iniciaEntrega;

    @Autowired
    public EntregaResource(RegistraEntregaController registraEntrega,
                           ConcluiEntregaController concluiEntrega,
                           CancelaEntregaController cancelaEntrega,
                           IniciaEntregaController iniciaEntrega) {
        this.registraEntrega = registraEntrega;
        this.concluiEntrega = concluiEntrega;
        this.cancelaEntrega = cancelaEntrega;
        this.iniciaEntrega = iniciaEntrega;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntregaDTO> registra(@RequestBody @Validated EntregaDTO requestDTO) {
        EntregaDTO responseDTO = registraEntrega.run(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping(value = "/conclusao/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> conclui(@PathVariable Long id) {
        concluiEntrega.run(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping(value = "/cancelamento/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> cancela(@PathVariable Long id) {
        cancelaEntrega.run(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/inicia-pendentes")
    public ResponseEntity<String> iniciaEntregaPendentes() {
        iniciaEntrega.run();
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

}
