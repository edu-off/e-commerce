package br.com.ms.cliente.adapters.gateways;

import br.com.ms.cliente.application.gateways.ClienteGateway;
import br.com.ms.cliente.application.gateways.EnderecoGateway;
import br.com.ms.cliente.domain.entities.ClienteEntity;
import br.com.ms.cliente.domain.entities.EnderecoEntity;
import br.com.ms.cliente.infrastructure.models.ClienteModel;
import br.com.ms.cliente.infrastructure.models.EnderecoModel;
import br.com.ms.cliente.infrastructure.repositories.EnderecoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Component
public class EnderecoGawateyImpl implements EnderecoGateway {

    private final EnderecoRepository enderecoRepository;
    private final ModelMapper mapper;

    @Autowired
    public EnderecoGawateyImpl(EnderecoRepository enderecoRepository, ModelMapper mapper) {
        this.enderecoRepository = enderecoRepository;
        this.mapper = mapper;
    }

    @Override
    public EnderecoEntity salvarEndereco(EnderecoEntity enderecoEntity) {
        ClienteModel clienteModel = new ClienteModel();
        clienteModel.setId(enderecoEntity.getCliente().getId());
        EnderecoModel enderecoModel = mapper.map(enderecoEntity, EnderecoModel.class);
        enderecoModel.setId(null);
        enderecoModel.setCliente(clienteModel);
        EnderecoModel enderecoModelCreated = enderecoRepository.save(enderecoModel);
        return mapper.map(enderecoModelCreated, EnderecoEntity.class);
    }

    @Override
    public EnderecoEntity atualizarEndereco(Long id, EnderecoEntity enderecoEntity) {
        Optional<EnderecoModel> optionalEnderecoModel = enderecoRepository.findById(id);
        if (optionalEnderecoModel.isEmpty())
            throw new NoSuchElementException("endereço não encontrado");

        EnderecoModel enderecoModel = optionalEnderecoModel.get();
        enderecoModel.setLogradouro(enderecoEntity.getLogradouro());
        enderecoModel.setBairro(enderecoEntity.getBairro());
        enderecoModel.setCidade(enderecoEntity.getCidade());
        enderecoModel.setUf(enderecoEntity.getUf());
        enderecoModel.setCep(enderecoEntity.getCep());

        EnderecoModel enderecoModelUpdated = enderecoRepository.save(enderecoModel);
        return mapper.map(enderecoModelUpdated, EnderecoEntity.class);
    }

    @Override
    public EnderecoEntity buscaEnderecoPorCliente(Long clienteId) {
        List<EnderecoModel> enderecos = enderecoRepository.findByClienteId(clienteId);
        EnderecoModel enderecoModel = enderecos.stream().findFirst().orElse(null);
        if (Objects.isNull(enderecoModel))
            throw new NoSuchElementException("endereço não encontrado");
        return mapper.map(enderecoModel, EnderecoEntity.class);
    }

}
