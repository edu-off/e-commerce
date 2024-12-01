package br.com.ms.logistica.adapters.resources;

import br.com.ms.logistica.adapters.controllers.AtualizaEntregadorController;
import br.com.ms.logistica.adapters.controllers.RegistraEntregadorController;
import br.com.ms.logistica.application.dto.EntregaDTO;
import br.com.ms.logistica.application.dto.EntregadorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/entregador")
public class EntregadorResource {

    private final RegistraEntregadorController registraEntregador;
    private final AtualizaEntregadorController atualizaEntregador;

    @Autowired
    public EntregadorResource(RegistraEntregadorController registraEntregador,
                              AtualizaEntregadorController atualizaEntregador) {
        this.registraEntregador = registraEntregador;
        this.atualizaEntregador = atualizaEntregador;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntregadorDTO> registra(@RequestBody @Validated EntregadorDTO requestDTO) {
        EntregadorDTO responseDTO = registraEntregador.run(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntregadorDTO> atualiza(@PathVariable Long id, @RequestBody @Validated EntregadorDTO requestDTO) {
        EntregadorDTO responseDTO = atualizaEntregador.run(id, requestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

}
