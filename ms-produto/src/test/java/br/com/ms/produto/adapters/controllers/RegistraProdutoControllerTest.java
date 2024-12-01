package br.com.ms.produto.adapters.controllers;

import br.com.ms.produto.adapters.presenters.ProdutoPresenter;
import br.com.ms.produto.application.dto.ProdutoDTO;
import br.com.ms.produto.application.usecases.SalvaProduto;
import br.com.ms.produto.application.usecases.ValidaProduto;
import br.com.ms.produto.domain.entities.ProdutoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

public class RegistraProdutoControllerTest {

    @Mock
    private ValidaProduto validaProduto;

    @Mock
    private SalvaProduto salvaProduto;

    @Mock
    private ProdutoPresenter presenter;

    @InjectMocks
    private RegistraProdutoController registraProdutoController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class Validacoes {

        @Test
        @DisplayName("Deve lançar exceção para produto inválido")
        public void deveLancarExcecaoParaProdutoInvalido() {
            ProdutoDTO produtoDTO = new ProdutoDTO(null, "", "descricao", 1.1, 1, "categoria");
            when(validaProduto.run(produtoDTO)).thenThrow(new IllegalArgumentException("erro ao validar produto: nome inválido"));
            assertThatThrownBy(() ->registraProdutoController.run(produtoDTO))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("erro ao validar produto: nome inválido");
        }

    }

    @Nested
    public class SalvandoProduto {

        @Test
        @DisplayName("Deve salvar produto corretamente")
        public void deveSalvarProdutoCorretamente() {
            ProdutoDTO produtoDTO = new ProdutoDTO(null, "nome", "descricao", 1.1, 1, "categoria");
            ProdutoEntity produtoEntity = new ProdutoEntity("nome", "descricao", 1.1, 1, "categoria");
            when(validaProduto.run(produtoDTO)).thenReturn(produtoEntity);
            when(salvaProduto.run(produtoEntity)).thenReturn(produtoEntity);
            when(presenter.transform(produtoEntity)).thenReturn(produtoDTO);
            ProdutoDTO produtoDTOCreated = registraProdutoController.run(produtoDTO);
            assertThat(produtoDTOCreated).isInstanceOf(ProdutoDTO.class).isNotNull();
        }

    }

}
