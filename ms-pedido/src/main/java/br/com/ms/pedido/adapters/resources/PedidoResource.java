package br.com.ms.pedido.adapters.resources;

import br.com.ms.pedido.adapters.controllers.*;
import br.com.ms.pedido.application.dto.PedidoDTO;
import br.com.ms.pedido.application.dto.ProdutoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedido")
public class PedidoResource {

    private final RegistraPedidoController registraPedidoController;
    private final AtualizaListaProdutosController atualizaListaProdutosController;
    private final ConfirmaPedidoController confirmaPedidoController;
    private final CancelaPedidoController cancelaPedidoController;
    private final ConcluiPedidoController concluiPedidoController;

    @Autowired
    public PedidoResource(RegistraPedidoController registraPedidoController,
                          AtualizaListaProdutosController atualizaListaProdutosController,
                          ConfirmaPedidoController confirmaPedidoController,
                          CancelaPedidoController cancelaPedidoController,
                          ConcluiPedidoController concluiPedidoController) {
        this.registraPedidoController = registraPedidoController;
        this.atualizaListaProdutosController = atualizaListaProdutosController;
        this.confirmaPedidoController = confirmaPedidoController;
        this.cancelaPedidoController = cancelaPedidoController;
        this.concluiPedidoController = concluiPedidoController;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PedidoDTO> registra(@RequestBody @Validated PedidoDTO requestDTO) {
        PedidoDTO responseDTO = registraPedidoController.run(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping(value = "/{id}/produtos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PedidoDTO> atualizaListaProdutos(@PathVariable Long id, @RequestBody @Validated List<ProdutoDTO> requestDTO) {
        PedidoDTO responseDTO = atualizaListaProdutosController.run(id, requestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PutMapping(value = "confirmacao/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> confirma(@PathVariable Long id) {
        confirmaPedidoController.run(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping(value = "cancelamento/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> cancela(@PathVariable Long id) {
        cancelaPedidoController.run(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping(value = "conclusao/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> conclui(@PathVariable Long id) {
        concluiPedidoController.run(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
