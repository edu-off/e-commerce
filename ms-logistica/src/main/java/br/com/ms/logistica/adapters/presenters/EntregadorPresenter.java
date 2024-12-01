package br.com.ms.logistica.adapters.presenters;

import br.com.ms.logistica.application.dto.EntregadorDTO;
import br.com.ms.logistica.domain.entities.EntregadorEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntregadorPresenter {

    private final ModelMapper mapper;

    @Autowired
    public EntregadorPresenter(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public EntregadorDTO transform(EntregadorEntity entity) {
        return mapper.map(entity, EntregadorDTO.class);
    }

}
