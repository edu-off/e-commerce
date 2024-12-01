package br.com.ms.logistica.adapters.gateways;

import br.com.ms.logistica.application.gateways.EntregaGateway;
import br.com.ms.logistica.domain.entities.EntregaEntity;
import br.com.ms.logistica.domain.entities.EntregadorEntity;
import br.com.ms.logistica.domain.enums.StatusEntrega;
import br.com.ms.logistica.infrastructure.models.EntregaModel;
import br.com.ms.logistica.infrastructure.models.EntregadorModel;
import br.com.ms.logistica.infrastructure.repositories.EntregaRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class EntregaGatewayImpl implements EntregaGateway {

    private final EntregaRepository entregaRepository;
    private final ModelMapper mapper;

    public EntregaGatewayImpl(EntregaRepository entregaRepository, ModelMapper mapper) {
        this.entregaRepository = entregaRepository;
        this.mapper = mapper;
    }

    @Override
    public EntregaEntity salvar(EntregaEntity entregaEntity) {
        EntregaModel model = mapper.map(entregaEntity, EntregaModel.class);
        model.setId(null);
        return mapper.map(entregaRepository.save(model), EntregaEntity.class);
    }

    @Override
    public EntregaEntity atualizar(Long id, EntregaEntity entregaEntity) {
        Optional<EntregaModel> optional = entregaRepository.findById(id);
        if (optional.isEmpty())
            throw new NoSuchElementException("entrega não encontrada");
        EntregaModel model = optional.get();
        model.setStatus(entregaEntity.getStatus());
        model.setDestinatario(entregaEntity.getDestinatario());
        model.setDdd(entregaEntity.getDdd());
        model.setTelefone(entregaEntity.getTelefone());
        model.setLogradouro(entregaEntity.getLogradouro());
        model.setBairro(entregaEntity.getBairro());
        model.setCidade(entregaEntity.getCidade());
        model.setUf(entregaEntity.getUf());
        model.setCep(entregaEntity.getCep());
        if (Objects.isNull(model.getEntregador()) && Objects.nonNull(entregaEntity.getEntregador())) {
            EntregadorModel entregadorModel = EntregadorModel.builder()
                    .id(entregaEntity.getEntregador().getId())
                    .nome(entregaEntity.getEntregador().getNome())
                    .email(entregaEntity.getEntregador().getEmail())
                    .status(entregaEntity.getEntregador().getStatus())
                    .build();
            model.setEntregador(entregadorModel);
        }
        EntregaEntity entregaEntityUpdated = mapper.map(entregaRepository.save(model), EntregaEntity.class);
        entregaEntityUpdated.setEntregador(entregaEntity.getEntregador());
        return entregaEntityUpdated;
    }

    @Override
    public EntregaEntity buscarPorId(Long id) {
        Optional<EntregaModel> optional = entregaRepository.findById(id);
        if (optional.isEmpty())
            throw new NoSuchElementException("entrega não encontrada");
        EntregaEntity entity = mapper.map(optional.get(), EntregaEntity.class);
        if (Objects.nonNull(optional.get().getEntregador()))
            entity.setEntregador(mapper.map(optional.get().getEntregador(), EntregadorEntity.class));
        return entity;
    }

    @Override
    public List<EntregaEntity> buscarPorStatus(StatusEntrega status) {
        List<EntregaEntity> entities = new ArrayList<>();
        List<EntregaModel> models = entregaRepository.findByStatus(status);
        models.forEach(model -> entities.add(mapper.map(model, EntregaEntity.class)));
        return entities;
    }

}
