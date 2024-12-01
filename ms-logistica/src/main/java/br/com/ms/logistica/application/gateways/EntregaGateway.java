package br.com.ms.logistica.application.gateways;

import br.com.ms.logistica.domain.entities.EntregaEntity;
import br.com.ms.logistica.domain.enums.StatusEntrega;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface EntregaGateway {

    EntregaEntity salvar(EntregaEntity entregaEntity);
    EntregaEntity atualizar(Long id, EntregaEntity entregaEntity);
    EntregaEntity buscarPorId(Long id);
    List<EntregaEntity> buscarPorStatus(StatusEntrega status);

}
