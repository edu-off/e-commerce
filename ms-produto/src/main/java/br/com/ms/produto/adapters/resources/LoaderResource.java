package br.com.ms.produto.adapters.resources;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/loader")
public class LoaderResource {

    private final JobLauncher jobLauncher;
    private final Job job;

    public LoaderResource(@Qualifier("jobCargaProduto") Job job, JobLauncher jobLauncher) {
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    @GetMapping("/produtos")
    public ResponseEntity<String> cargaProdutos() throws Exception {
        JobParameters parameters = new JobParametersBuilder()
                .addLocalDateTime("date.time", LocalDateTime.now())
                .toJobParameters();
        jobLauncher.run(job, parameters);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

}
