package br.com.ms.logistica.adapters.clients;

import br.com.ms.logistica.adapters.handler.ErrorDecoderConfig;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "clientFeignPedido", url = "${pedido.url}", configuration = ErrorDecoderConfig.class)
public interface PedidoFeignClient {

    @PutMapping(value = "/e-commerce/pedido/conclusao/{id}")
    @Headers("Content-Type: application/json")
    void concluiPedido(@PathVariable Long id);

    @PutMapping(value = "/e-commerce/pedido/cancelamento/{id}")
    @Headers("Content-Type: application/json")
    void cancelaPedido(@PathVariable Long id);

}
