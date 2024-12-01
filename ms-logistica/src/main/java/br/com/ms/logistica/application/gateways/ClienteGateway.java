package br.com.ms.logistica.application.gateways;

import br.com.ms.logistica.application.dto.ClienteDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

@Component
public interface ClienteGateway {

    ClienteDTO buscaPorId(@PathVariable Long id);

}
