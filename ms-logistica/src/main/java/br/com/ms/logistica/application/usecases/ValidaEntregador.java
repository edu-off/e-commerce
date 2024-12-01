package br.com.ms.logistica.application.usecases;

import br.com.ms.logistica.application.dto.EntregadorDTO;
import br.com.ms.logistica.application.exceptions.EntregadorException;
import br.com.ms.logistica.domain.entities.EntregadorEntity;
import br.com.ms.logistica.domain.enums.StatusEntregador;
import org.springframework.stereotype.Service;

@Service
public class ValidaEntregador {

    public EntregadorEntity run(EntregadorDTO dto) {
        EntregadorEntity entity = null;
        try {
            entity = new EntregadorEntity(StatusEntregador.get(dto.getStatus()), dto.getNome(), dto.getEmail());
        } catch(IllegalArgumentException exception) {
            throw new EntregadorException("erro ao validar entregador: " + exception.getMessage());
        }
        return entity;
    }

}
