package br.com.ms.cliente.adapters.gateways;

import br.com.ms.cliente.application.gateways.ClienteGateway;
import br.com.ms.cliente.domain.entities.ClienteEntity;
import br.com.ms.cliente.domain.enums.StatusCliente;
import br.com.ms.cliente.infrastructure.models.ClienteModel;
import br.com.ms.cliente.infrastructure.repositories.ClienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class ClienteGawateyImpl implements ClienteGateway {

    private final ClienteRepository clienteRepository;
    private final ModelMapper mapper;

    @Autowired
    public ClienteGawateyImpl(ClienteRepository clienteRepository, ModelMapper mapper) {
        this.clienteRepository = clienteRepository;
        this.mapper = mapper;
    }

    @Override
    public ClienteEntity salvarCliente(ClienteEntity clienteEntity) {
        ClienteModel clienteModel = mapper.map(clienteEntity, ClienteModel.class);
        clienteModel.setId(null);
        ClienteModel clienteModelCreated = clienteRepository.save(clienteModel);
        return mapper.map(clienteModelCreated, ClienteEntity.class);
    }

    @Override
    public ClienteEntity atualizarCliente(Long id, ClienteEntity clienteEntity) {
        Optional<ClienteModel> optionalClienteModel = clienteRepository.findById(id);
        if (optionalClienteModel.isEmpty())
            throw new NoSuchElementException("cliente não encontrado");

        ClienteModel clienteModel = optionalClienteModel.get();
        clienteModel.setNome(clienteEntity.getNome());
        clienteModel.setStatus(clienteEntity.getStatus());
        clienteModel.setEmail(clienteEntity.getEmail());
        clienteModel.setDdd(clienteEntity.getDdd());
        clienteModel.setTelefone(clienteEntity.getTelefone());

        ClienteModel clienteModelUpdated = clienteRepository.save(clienteModel);
        return mapper.map(clienteModelUpdated, ClienteEntity.class);
    }

    @Override
    public ClienteEntity buscaCliente(Long id) {
        Optional<ClienteModel> optionalClienteModel = clienteRepository.findById(id);
        if (optionalClienteModel.isEmpty())
            throw new NoSuchElementException("cliente não encontrado");
        return mapper.map(optionalClienteModel.get(), ClienteEntity.class);
    }

    @Override
    public Page<ClienteEntity> buscaClientePorEmail(String email, Pageable pageable) {
        Page<ClienteModel> clientes = clienteRepository.findByEmailContainingIgnoreCase(email, pageable);
        return transformPageOfModelToPageOfDomain(clientes, pageable);
    }

    @Override
    public Page<ClienteEntity> buscaClientePorNome(String nome, Pageable pageable) {
        Page<ClienteModel> clientes = clienteRepository.findByNomeContainingIgnoreCase(nome, pageable);
        return transformPageOfModelToPageOfDomain(clientes, pageable);
    }

    @Override
    public Page<ClienteEntity> buscaClientePorStatus(StatusCliente status, Pageable pageable) {
        Page<ClienteModel> clientes = clienteRepository.findByStatus(status, pageable);
        return transformPageOfModelToPageOfDomain(clientes, pageable);
    }

    private Page<ClienteEntity> transformPageOfModelToPageOfDomain(Page<ClienteModel> queryResults, Pageable pageable) {
        List<ClienteModel> models = queryResults.getContent();
        List<ClienteEntity> restaurantes = new ArrayList<>();
        models.forEach(model -> restaurantes.add(mapper.map(model, ClienteEntity.class)));
        return PageableExecutionUtils.getPage(restaurantes, pageable, restaurantes::size);
    }
}
