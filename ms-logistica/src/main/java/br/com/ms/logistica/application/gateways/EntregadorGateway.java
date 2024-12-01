package br.com.ms.logistica.application.gateways;

import br.com.ms.logistica.domain.entities.EntregadorEntity;
import br.com.ms.logistica.domain.enums.StatusEntregador;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface EntregadorGateway {

    EntregadorEntity salvar(EntregadorEntity entregadorEntity);
    EntregadorEntity atualizar(Long id, EntregadorEntity entregadorEntity);
    EntregadorEntity buscarPorId(Long id);
    List<EntregadorEntity> buscarPorStatus(StatusEntregador status);

}
