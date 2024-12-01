package br.com.ms.logistica.adapters.presenters;

import br.com.ms.logistica.application.dto.EntregaDTO;
import br.com.ms.logistica.domain.entities.EntregaEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntregaPresenter {

    private final ModelMapper mapper;

    @Autowired
    public EntregaPresenter(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public EntregaDTO transform(EntregaEntity entity) {
        return mapper.map(entity, EntregaDTO.class);
    }

}
