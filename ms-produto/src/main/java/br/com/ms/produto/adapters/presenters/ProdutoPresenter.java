package br.com.ms.produto.adapters.presenters;

import br.com.ms.produto.application.dto.ProdutoDTO;
import br.com.ms.produto.domain.entities.ProdutoEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProdutoPresenter {

    private final ModelMapper mapper;

    @Autowired
    public ProdutoPresenter(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public ProdutoDTO transform(ProdutoEntity entity) {
        ProdutoDTO produtoDTO = mapper.map(entity, ProdutoDTO.class);
        return produtoDTO;
    }

    public Page<ProdutoDTO> transform(Page<ProdutoEntity> entities, Pageable pageable) {
        List<ProdutoDTO> dtos = new ArrayList<>();
        entities.getContent().forEach(entity -> dtos.add(mapper.map(entity, ProdutoDTO.class)));
        return PageableExecutionUtils.getPage(dtos, pageable, dtos::size);
    }

}
