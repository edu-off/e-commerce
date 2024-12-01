package br.com.ms.pedido.adapters.clients;

import br.com.ms.pedido.adapters.handler.ErrorDecoderConfig;
import br.com.ms.pedido.application.dto.ProdutoDTO;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@Component
@FeignClient(name = "clientFeignProduto", url = "${produto.url}", configuration = ErrorDecoderConfig.class)
public interface ProdutoFeignClient {

    @GetMapping(value = "/e-commerce/produto/{id}")
    @Headers("Content-Type: application/json")
    ProdutoDTO buscaProduto(@PathVariable Long id);

    @PutMapping(value = "/e-commerce/produto/{id}/estoque/adiciona/{quantidade}")
    @Headers("Content-Type: application/json")
    void incrementaEstoque(@PathVariable Long id, @PathVariable Integer quantidade);

    @PutMapping(value = "/e-commerce/produto/{id}/estoque/remove/{quantidade}")
    @Headers("Content-Type: application/json")
    void decrementaEstoque(@PathVariable Long id, @PathVariable Integer quantidade);

}
