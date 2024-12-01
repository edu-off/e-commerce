package br.com.ms.produto.batch.steps;

import br.com.ms.produto.application.dto.ProdutoDTO;
import br.com.ms.produto.application.usecases.ValidaProduto;
import br.com.ms.produto.batch.processors.ProdutoProcessor;
import br.com.ms.produto.batch.readers.FileReader;
import br.com.ms.produto.batch.writers.RepositoryWriter;
import br.com.ms.produto.infrastructure.models.ProdutoModel;
import br.com.ms.produto.infrastructure.repositories.ProdutoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class CargaProdutoStepConfig {

    private final ProdutoRepository produtoRepository;
    private final ValidaProduto validaProduto;
    private final ModelMapper mapper;

    @Value("${spring.batch.file.name}")
    private String fileName;

    @Value("${spring.batch.fields.file}")
    private String fields;

    @Autowired
    public CargaProdutoStepConfig(ProdutoRepository produtoRepository,
                                  ValidaProduto validaProduto,
                                  ModelMapper mapper){
        this.produtoRepository = produtoRepository;
        this.validaProduto = validaProduto;
        this.mapper = mapper;
    }

    @Bean
    protected Step stepCargaProduto(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("stepCargaProduto", jobRepository)
                .<ProdutoDTO, ProdutoModel> chunk(1024, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<ProdutoDTO> reader() {
        FileReader reader = new FileReader(fileName, fields.split(";"));
        return reader.build();
    }

    @Bean
    @StepScope
    public ProdutoProcessor<ProdutoDTO, ProdutoModel> processor() {
        ProdutoProcessor<ProdutoDTO, ProdutoModel> processor = new ProdutoProcessor<>(validaProduto, mapper);
        return processor.build();
    }

    @Bean
    @StepScope
    public RepositoryItemWriter<ProdutoModel> writer() {
        RepositoryWriter<ProdutoModel, Long> repositoryWriter = new RepositoryWriter<>(produtoRepository);
        return repositoryWriter.build();
    }

}
