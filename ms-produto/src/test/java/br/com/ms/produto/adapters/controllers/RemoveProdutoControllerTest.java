package br.com.ms.produto.adapters.controllers;

import br.com.ms.produto.application.usecases.RemoveProduto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class RemoveProdutoControllerTest {

    @Mock
    private RemoveProduto removeProduto;

    @InjectMocks
    private RemoveProdutoController removeProdutoController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class Remocao {

        @Test
        @DisplayName("Deve lançar exceção para produto inexistente")
        public void deveLancarExcecaoParaProdutoInexistente() {
            doThrow(new NoSuchElementException("produto não encontrado")).when(removeProduto).run(0L);
            assertThatThrownBy(() -> removeProdutoController.run(0L))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("produto não encontrado");
        }

        @Test
        @DisplayName("Deve remover produto")
        public void deveRemoverProduto() {
            doNothing().when(removeProduto).run(1L);
            removeProdutoController.run(1L);
            verify(removeProduto, times(1)).run(1L);
        }

    }

}
