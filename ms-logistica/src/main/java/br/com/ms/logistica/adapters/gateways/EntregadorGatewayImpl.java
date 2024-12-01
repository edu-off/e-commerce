package br.com.ms.logistica.adapters.gateways;

import br.com.ms.logistica.application.gateways.EntregadorGateway;
import br.com.ms.logistica.domain.entities.EntregadorEntity;
import br.com.ms.logistica.domain.enums.StatusEntregador;
import br.com.ms.logistica.infrastructure.models.EntregadorModel;
import br.com.ms.logistica.infrastructure.repositories.EntregadorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class EntregadorGatewayImpl implements EntregadorGateway {

    private final EntregadorRepository entregadorRepository;
    private final ModelMapper mapper;

    public EntregadorGatewayImpl(EntregadorRepository entregadorRepository, ModelMapper mapper) {
        this.entregadorRepository = entregadorRepository;
        this.mapper = mapper;
    }

    @Override
    public EntregadorEntity salvar(EntregadorEntity entregadorEntity) {
        EntregadorModel model = mapper.map(entregadorEntity, EntregadorModel.class);
        model.setId(null);
        return mapper.map(entregadorRepository.save(model), EntregadorEntity.class);
    }

    @Override
    public EntregadorEntity atualizar(Long id, EntregadorEntity entregadorEntity) {
        Optional<EntregadorModel> optional = entregadorRepository.findById(id);
        if (optional.isEmpty())
            throw new NoSuchElementException("entregador não encontrado");
        EntregadorModel model = optional.get();
        model.setStatus(entregadorEntity.getStatus());
        model.setNome(entregadorEntity.getNome());
        model.setEmail(entregadorEntity.getEmail());
        return mapper.map(entregadorRepository.save(model), EntregadorEntity.class);
    }

    @Override
    public EntregadorEntity buscarPorId(Long id) {
        Optional<EntregadorModel> optional = entregadorRepository.findById(id);
        if (optional.isEmpty())
            throw new NoSuchElementException("entregador não encontrado");
        return mapper.map(optional.get(), EntregadorEntity.class);
    }

    @Override
    public List<EntregadorEntity> buscarPorStatus(StatusEntregador status) {
        List<EntregadorEntity> entities = new ArrayList<>();
        List<EntregadorModel> models = entregadorRepository.findByStatus(status);
        models.forEach(model -> entities.add(mapper.map(model, EntregadorEntity.class)));
        return entities;
    }

}
