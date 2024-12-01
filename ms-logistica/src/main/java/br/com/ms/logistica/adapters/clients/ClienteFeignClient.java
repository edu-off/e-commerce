package br.com.ms.logistica.adapters.clients;

import br.com.ms.logistica.adapters.handler.ErrorDecoderConfig;
import br.com.ms.logistica.application.dto.ClienteDTO;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "clientFeignCliente", url = "${cliente.url}", configuration = ErrorDecoderConfig.class)
public interface ClienteFeignClient {

    @GetMapping(value = "/e-commerce/cliente/{id}")
    @Headers("Content-Type: application/json")
    ClienteDTO buscaPorId(@PathVariable Long id);

}
