package br.com.ms.produto;

import br.com.ms.produto.application.usecases.ValidaProduto;
import br.com.ms.produto.infrastructure.repositories.ProdutoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "br.com.ms.produto.adapters",
        "br.com.ms.produto.application",
        "br.com.ms.produto.batch",
        "br.com.ms.produto.domain",
        "br.com.ms.produto.infrastructure",
})
public class MsProdutoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsProdutoApplication.class, args);
    }

}
