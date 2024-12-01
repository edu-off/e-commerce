package br.com.ms.produto.adapters.controllers;

import br.com.ms.produto.application.dto.ProdutoDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
@AutoConfigureTestDatabase
public class AtualizaProdutoControllerIT {

    @Autowired
    private AtualizaProdutoController atualizaProdutoController;

    @Autowired
    private RegistraProdutoController registraProdutoController;

    @Nested
    public class Atualizacao {

        @Test
        @DisplayName("Deve lançar exceção para produto inexistente")
        public void deveLancarExcecaoParaProdutoInexistente() {
            ProdutoDTO produtoDTO = new ProdutoDTO(0L, "nome", "descricao", 1.1, 1, "categoria");
            assertThatThrownBy(() -> atualizaProdutoController.run(0L, produtoDTO))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("produto não encontrado");
        }

        @Test
        @DisplayName("Deve atualizar produto")
        public void deveAtualizarProduto() {
            ProdutoDTO produtoDTO = registraProdutoController.run(ProdutoDTO.builder()
                    .nome("nome")
                    .descricao("descricao")
                    .preco(1.1)
                    .quantidade(1)
                    .categoria("categora")
                    .build());

            produtoDTO.setNome("nome atualizado");
            atualizaProdutoController.run(produtoDTO.getId(), produtoDTO);
        }

    }

}
