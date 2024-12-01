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
public class RemoveProdutoControllerIT {

    @Autowired
    private RegistraProdutoController registraProdutoController;

    @Autowired
    private RemoveProdutoController removeProdutoController;

    @Nested
    public class Remocao {

        @Test
        @DisplayName("Deve lançar exceção para produto inexistente")
        public void deveLancarExcecaoParaProdutoInexistente() {
            assertThatThrownBy(() -> removeProdutoController.run(0L))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("produto não encontrado");
        }

        @Test
        @DisplayName("Deve remover produto")
        public void deveRemoverProduto() {
            ProdutoDTO produtoDTO = registraProdutoController.run(ProdutoDTO.builder()
                    .nome("nome")
                    .descricao("descricao")
                    .preco(1.1)
                    .quantidade(1)
                    .categoria("categora")
                    .build());

            removeProdutoController.run(produtoDTO.getId());
        }

    }

}
