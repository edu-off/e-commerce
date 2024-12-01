package br.com.ms.cliente.infrastructure.repositories;

import br.com.ms.cliente.infrastructure.models.EnderecoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnderecoRepository extends JpaRepository<EnderecoModel, Long> {

    @Query("select endereco from EnderecoModel endereco where endereco.cliente.id = :clienteId")
    List<EnderecoModel> findByClienteId(Long clienteId);

}
