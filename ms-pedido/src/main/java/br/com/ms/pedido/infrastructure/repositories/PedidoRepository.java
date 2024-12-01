package br.com.ms.pedido.infrastructure.repositories;

import br.com.ms.pedido.infrastructure.models.PedidoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoModel, Long> {
}
