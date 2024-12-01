package br.com.ms.pedido.adapters.clients;

import br.com.ms.pedido.adapters.handler.ErrorDecoderConfig;
import br.com.ms.pedido.application.dto.EntregaDTO;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(name = "clientFeignEntrega", url = "${entrega.url}", configuration = ErrorDecoderConfig.class)
public interface EntregaFeignClient {

    @GetMapping(value = "/e-commerce/entrega")
    @Headers("Content-Type: application/json")
    EntregaDTO registraEntrega(@RequestBody EntregaDTO entregaDTO);

}
