package br.com.ms.produto.adapters.controllers;

import br.com.ms.produto.application.dto.ProdutoDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@AutoConfigureTestDatabase
public class BuscaProdutoControllerIT {

    @Autowired
    private BuscaProdutoController buscaProdutoController;

    @Autowired
    private RegistraProdutoController registraProdutoController;

    private ProdutoDTO produtoDTOSetup;

    @BeforeEach
    public void setup() {
        ProdutoDTO produtoDTO1 = new ProdutoDTO(null, "teste 1", "descricao", 1.1, 1, "categoria");
        ProdutoDTO produtoDTO2 = new ProdutoDTO(null, "teste 2", "descricao", 1.1, 1, "categoria");
        ProdutoDTO produtoDTO3 = new ProdutoDTO(null, "nome 1", "teste", 1.1, 1, "teste");
        ProdutoDTO produtoDTO4 = new ProdutoDTO(null, "nome 2", "teste", 1.1, 1, "teste");
        produtoDTOSetup = registraProdutoController.run(produtoDTO1);
        registraProdutoController.run(produtoDTO2);
        registraProdutoController.run(produtoDTO3);
        registraProdutoController.run(produtoDTO4);
    }

    @Nested
    public class Buscas {

        @Test
        @DisplayName("deve buscar produto por id")
        public void deveBuscarProdutoPorId() {
            ProdutoDTO produtoDTORetrieved = buscaProdutoController.porId(produtoDTOSetup.getId());
            assertThat(produtoDTORetrieved).isInstanceOf(ProdutoDTO.class).isNotNull();
            assertThat(produtoDTORetrieved.getId()).isNotZero().isEqualTo(produtoDTOSetup.getId());
        }

        @Test
        @DisplayName("deve buscar produtos por nome")
        public void deveBuscarProdutosPorNome() {
            Pageable pageable = PageRequest.of(0, 10);
            Page<ProdutoDTO> produtosDTORetrieved = buscaProdutoController.porNome("nome", pageable);
            assertThat(produtosDTORetrieved).isInstanceOf(Page.class).isNotEmpty().hasSize(2);
        }


        @Test
        @DisplayName("deve buscar produtos por descricao")
        public void deveBuscarProdutosPorDescricao() {
            Pageable pageable = PageRequest.of(0, 10);
            Page<ProdutoDTO> produtosDTORetrieved = buscaProdutoController.porDescricao("descricao", pageable);
            assertThat(produtosDTORetrieved).isInstanceOf(Page.class).isNotEmpty().hasSize(2);
        }

        @Test
        @DisplayName("deve buscar produtos por categoria")
        public void deveBuscarProdutosPorCategoria() {
            Pageable pageable = PageRequest.of(0, 10);
            Page<ProdutoDTO> produtosDTORetrieved = buscaProdutoController.porCategoria("categoria", pageable);
            assertThat(produtosDTORetrieved).isInstanceOf(Page.class).isNotEmpty().hasSize(2);
        }

    }

}
