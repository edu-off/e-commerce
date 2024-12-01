package br.com.ms.logistica.infrastructure.repositories;

import br.com.ms.logistica.domain.enums.StatusEntrega;
import br.com.ms.logistica.domain.enums.StatusEntregador;
import br.com.ms.logistica.infrastructure.models.EntregaModel;
import br.com.ms.logistica.infrastructure.models.EntregadorModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntregaRepository extends JpaRepository<EntregaModel, Long> {

    List<EntregaModel> findByStatus(StatusEntrega status);

}
