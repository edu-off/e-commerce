package br.com.ms.produto.adapters.controllers;

import br.com.ms.produto.application.dto.ProdutoDTO;
import br.com.ms.produto.application.exceptions.ProdutoException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
@AutoConfigureTestDatabase
public class RegistraProdutoControllerIT {

    @Autowired
    private RegistraProdutoController registraProdutoController;

    @Nested
    public class Validacoes {

        @Test
        @DisplayName("Deve lançar exceção para produto inválido")
        public void deveLancarExcecaoParaProdutoInvalido() {
            ProdutoDTO produtoDTO = new ProdutoDTO(null, "", "descricao", 1.1, 1, "categoria");
            assertThatThrownBy(() -> registraProdutoController.run(produtoDTO))
                    .isInstanceOf(ProdutoException.class)
                    .hasMessage("erro ao validar produto: nome inválido");
        }

    }

    @Nested
    public class SalvandoProduto {

        @Test
        @DisplayName("Deve salvar produto corretamente")
        public void deveSalvarProdutoCorretamente() {
            ProdutoDTO produtoDTO = new ProdutoDTO(null, "nome", "descricao", 1.1, 1, "categoria");
            ProdutoDTO produtoDTOCreated = registraProdutoController.run(produtoDTO);
            assertThat(produtoDTOCreated).isInstanceOf(ProdutoDTO.class).isNotNull();
            assertThat(produtoDTOCreated.getId()).isNotNull().isNotZero();
        }

    }

}
