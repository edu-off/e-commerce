package br.com.ms.produto.batch.jobs;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CargaProdutoJobConfig {

    @Bean
    public Job jobCargaProduto(JobRepository jobRepository, @Qualifier("stepCargaProduto") Step firstStep) {
        return new JobBuilder("jobCargaProduto", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(firstStep)
                .build();
    }

}
