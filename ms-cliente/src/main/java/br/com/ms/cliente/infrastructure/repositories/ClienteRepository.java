package br.com.ms.cliente.infrastructure.repositories;

import br.com.ms.cliente.domain.enums.StatusCliente;
import br.com.ms.cliente.infrastructure.models.ClienteModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteModel, Long> {

    Page<ClienteModel> findByEmailContainingIgnoreCase(String email, Pageable pageable);
    Page<ClienteModel> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
    Page<ClienteModel> findByStatus(StatusCliente status, Pageable pageable);

}
