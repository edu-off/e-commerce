package br.com.ms.cliente.application.gateways;

import br.com.ms.cliente.domain.entities.ClienteEntity;
import br.com.ms.cliente.domain.enums.StatusCliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public interface ClienteGateway {

    ClienteEntity salvarCliente(ClienteEntity clienteEntity);
    ClienteEntity atualizarCliente(Long id, ClienteEntity clienteEntity);
    ClienteEntity buscaCliente(Long id);
    Page<ClienteEntity> buscaClientePorEmail(String email, Pageable pageable);
    Page<ClienteEntity> buscaClientePorNome(String nome, Pageable pageable);
    Page<ClienteEntity> buscaClientePorStatus(StatusCliente status, Pageable pageable);

}
