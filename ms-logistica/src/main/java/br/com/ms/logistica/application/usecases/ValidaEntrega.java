package br.com.ms.logistica.application.usecases;

import br.com.ms.logistica.application.dto.EntregaDTO;
import br.com.ms.logistica.application.exceptions.EntregaException;
import br.com.ms.logistica.domain.entities.EntregaEntity;
import br.com.ms.logistica.domain.enums.StatusEntrega;
import org.springframework.stereotype.Service;

@Service
public class ValidaEntrega {

    public EntregaEntity run(EntregaDTO dto) {
        EntregaEntity entity = null;
        try {
            entity = new EntregaEntity(StatusEntrega.get(dto.getStatus()), dto.getPedidoId(), dto.getClienteId());
        } catch(IllegalArgumentException exception) {
            throw new EntregaException("erro ao validar entrega: " + exception.getMessage());
        }
        return entity;
    }

}
