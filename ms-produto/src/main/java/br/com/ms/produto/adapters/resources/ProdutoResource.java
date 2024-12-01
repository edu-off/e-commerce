package br.com.ms.produto.adapters.resources;

import br.com.ms.produto.adapters.controllers.*;
import br.com.ms.produto.application.dto.ProdutoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produto")
public class ProdutoResource {

    private final RegistraProdutoController registraProduto;
    private final AtualizaProdutoController atualizaProduto;
    private final AtualizaEstoqueController atualizaEstoque;
    private final RemoveProdutoController removeProduto;
    private final BuscaProdutoController buscaProduto;

    @Autowired
    public ProdutoResource(RegistraProdutoController registraProduto,
                           AtualizaProdutoController atualizaProduto,
                           AtualizaEstoqueController atualizaEstoque,
                           RemoveProdutoController removeProduto,
                           BuscaProdutoController buscaProduto) {
        this.registraProduto = registraProduto;
        this.atualizaProduto = atualizaProduto;
        this.atualizaEstoque = atualizaEstoque;
        this.removeProduto = removeProduto;
        this.buscaProduto = buscaProduto;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProdutoDTO> registra(@RequestBody @Validated ProdutoDTO dto) {
        ProdutoDTO dtoCreated = registraProduto.run(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dtoCreated);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProdutoDTO> atualiza(@PathVariable Long id, @Validated @RequestBody ProdutoDTO dto) {
        ProdutoDTO dtoUpdated = atualizaProduto.run(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(dtoUpdated);
    }

    @PutMapping(value = "/{id}/estoque/adiciona/{quantidade}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProdutoDTO> incrementaEstoque(@PathVariable Long id, @PathVariable Integer quantidade) {
        ProdutoDTO dtoUpdated = atualizaEstoque.incrementa(id, quantidade);
        return ResponseEntity.status(HttpStatus.OK).body(dtoUpdated);
    }

    @PutMapping(value = "/{id}/estoque/remove/{quantidade}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProdutoDTO> decrementaEstoque(@PathVariable Long id, @PathVariable Integer quantidade) {
        ProdutoDTO dtoUpdated = atualizaEstoque.decrementa(id, quantidade);
        return ResponseEntity.status(HttpStatus.OK).body(dtoUpdated);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> remove(@PathVariable Long id) {
        removeProduto.run(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProdutoDTO> buscaPorId(@PathVariable Long id) {
        ProdutoDTO dto = buscaProduto.porId(id);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @GetMapping(value = "/nome/{nome}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ProdutoDTO>> buscaPorNome(@PathVariable String nome, Pageable pageable) {
        Page<ProdutoDTO> dtos = buscaProduto.porNome(nome, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @GetMapping(value = "/descricao/{descricao}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ProdutoDTO>> buscaPorDescricao(@PathVariable String descricao, Pageable pageable) {
        Page<ProdutoDTO> dtos = buscaProduto.porDescricao(descricao, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @GetMapping(value = "/categoria/{categoria}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ProdutoDTO>> buscaPorCategoria(@PathVariable String categoria, Pageable pageable) {
        Page<ProdutoDTO> dtos = buscaProduto.porCategoria(categoria, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

}
