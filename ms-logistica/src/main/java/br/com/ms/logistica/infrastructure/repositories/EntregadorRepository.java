package br.com.ms.logistica.infrastructure.repositories;

import br.com.ms.logistica.domain.enums.StatusEntregador;
import br.com.ms.logistica.infrastructure.models.EntregadorModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntregadorRepository extends JpaRepository<EntregadorModel, Long> {

    List<EntregadorModel> findByStatus(StatusEntregador status);

}
