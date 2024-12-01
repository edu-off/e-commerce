package br.com.ms.produto.adapters.gateways;

import br.com.ms.produto.application.gateways.ProdutoGateway;
import br.com.ms.produto.domain.entities.ProdutoEntity;
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

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
@AutoConfigureTestDatabase
public class ProdutoGatewayImplIT {

    @Autowired
    private ProdutoGateway produtoGateway;

    private ProdutoEntity produtoEntity1;

    @BeforeEach
    public void setUp() {
        produtoEntity1 = produtoGateway.salvar(new ProdutoEntity("nome 1", "descricao", 1.1, 1, "categoria"));
        produtoGateway.salvar(new ProdutoEntity("teste 2", "descricao", 1.1, 1, "categoria"));
        produtoGateway.salvar(new ProdutoEntity("teste 3", "teste", 1.1, 1, "teste"));
        produtoGateway.salvar(new ProdutoEntity("nome 4", "teste", 1.1, 1, "teste"));
    }

    @Nested
    public class SalvandoProduto {

        @Test
        @DisplayName("Deve salvar produto corretamente")
        public void deveSalvarProdutoCorretamente() {
            ProdutoEntity produtoEntity = new ProdutoEntity("nome", "descricao", 1.1, 1, "categoria");
            ProdutoEntity produtoEntityCreated = produtoGateway.salvar(produtoEntity);
            assertThat(produtoEntityCreated).isInstanceOf(ProdutoEntity.class).isNotNull();
            assertThat(produtoEntityCreated.getId()).isNotNull().isNotZero();
        }

    }

    @Nested
    public class AtualizandoProduto {

        @Test
        @DisplayName("Deve lançar exceçâo para produto inexistente")
        public void deveLancarExcecaoParaProdutoInexistente() {
            ProdutoEntity clienteEntity = new ProdutoEntity("nome", "descricao", 1.1, 1, "categoria");
            assertThatThrownBy(() -> produtoGateway.atualizar(0L, clienteEntity))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("produto não encontrado");
        }

        @Test
        @DisplayName("Deve atualizar produto corretamente")
        public void deveAtualizarProdutoCorretamente() {
            ProdutoEntity clienteEntity = new ProdutoEntity("nome", "descricao", 1.1, 1, "categoria");
            ProdutoEntity clienteEntityUpdated = produtoGateway.atualizar(produtoEntity1.getId(), clienteEntity);
            assertThat(clienteEntityUpdated).isInstanceOf(ProdutoEntity.class).isNotNull();
            assertThat(clienteEntityUpdated.getId()).isEqualTo(produtoEntity1.getId());
            assertThat(clienteEntityUpdated.getNome()).isEqualTo(clienteEntity.getNome());
            assertThat(clienteEntityUpdated.getDescricao()).isEqualTo(clienteEntity.getDescricao());
        }

    }

    @Nested
    public class RemovendoProduto {

        @Test
        @DisplayName("Deve lançar exceçâo para produto inexistente")
        public void deveLancarExcecaoParaProdutoInexistente() {
            assertThatThrownBy(() -> produtoGateway.remover(0L))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("produto não encontrado");
        }

        @Test
        @DisplayName("Deve remover produto corretamente")
        public void deveAtualizarProdutoCorretamente() {
            Long id = produtoEntity1.getId();
            produtoGateway.remover(id);
            assertThat(produtoGateway.buscarPorId(id)).isNull();
        }

    }

    @Nested
    public class BuscandoProdutos {

        @Test
        @DisplayName("Deve buscar produto por id")
        public void deveBuscarProdutoPorId() {
            ProdutoEntity produtoEntity = produtoGateway.buscarPorId(produtoEntity1.getId());
            assertThat(produtoEntity).isInstanceOf(ProdutoEntity.class).isNotNull();
            assertThat(produtoEntity.getId()).isEqualTo(produtoEntity1.getId());
            assertThat(produtoEntity.getNome()).isEqualTo(produtoEntity1.getNome());
            assertThat(produtoEntity.getDescricao()).isEqualTo(produtoEntity1.getDescricao());
            assertThat(produtoEntity.getPreco()).isEqualTo(produtoEntity1.getPreco());
            assertThat(produtoEntity.getQuantidade()).isEqualTo(produtoEntity1.getQuantidade());
            assertThat(produtoEntity.getCategoria()).isEqualTo(produtoEntity1.getCategoria());
        }

        @Test
        @DisplayName("Deve buscar produtos por nome")
        public void deveBuscarProdutosPorNome() {
            Page<ProdutoEntity> produtos = produtoGateway.buscarPorNome("teste", PageRequest.of(0, 10));
            assertThat(produtos).isInstanceOf(Page.class).isNotNull().isNotEmpty().hasSize(2);
        }

        @Test
        @DisplayName("Deve buscar produtos por descricao")
        public void deveBuscarProdutosPorDescricao() {
            Page<ProdutoEntity> produtos = produtoGateway.buscarPorDescricao("teste", PageRequest.of(0, 10));
            assertThat(produtos).isInstanceOf(Page.class).isNotNull().isNotEmpty().hasSize(2);
        }

        @Test
        @DisplayName("Deve buscar produtos por categoria")
        public void deveBuscarProdutosPorCategoria() {
            Page<ProdutoEntity> produtos = produtoGateway.buscarPorCategoria("teste", PageRequest.of(0, 10));
            assertThat(produtos).isInstanceOf(Page.class).isNotNull().isNotEmpty().hasSize(2);
        }

    }

}
