package br.com.ms.produto.batch.processors;

import br.com.ms.produto.application.dto.ProdutoDTO;
import br.com.ms.produto.application.usecases.ValidaProduto;
import br.com.ms.produto.domain.entities.ProdutoEntity;
import br.com.ms.produto.infrastructure.models.ProdutoModel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.batch.item.ItemProcessor;

@AllArgsConstructor
public class ProdutoProcessor<T, S> implements ItemProcessor<ProdutoDTO, ProdutoModel> {

    private final ValidaProduto validaProduto;
    private final ModelMapper mapper;

    public ProdutoProcessor<T, S> build() {
        return this;
    }

    @Override
    public ProdutoModel process(ProdutoDTO produtoDTO) {
        ProdutoEntity produtoEntity = validaProduto.run(produtoDTO);
        return mapper.map(produtoEntity, ProdutoModel.class);
    }

}
