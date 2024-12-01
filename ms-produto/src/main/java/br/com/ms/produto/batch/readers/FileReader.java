package br.com.ms.produto.batch.readers;

import br.com.ms.produto.application.dto.ProdutoDTO;
import lombok.AllArgsConstructor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.core.io.ClassPathResource;

@AllArgsConstructor
public class FileReader extends FlatFileItemReader<ProdutoDTO> {

    private String fileName;
    private String[] fields;

    public FlatFileItemReader<ProdutoDTO> build() {
        BeanWrapperFieldSetMapper<ProdutoDTO> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(ProdutoDTO.class);

        return new FlatFileItemReaderBuilder<ProdutoDTO>()
                .name("produtoItemReader")
                .resource(new ClassPathResource(fileName))
                .delimited().delimiter(";").names(fields)
                .fieldSetMapper(fieldSetMapper).build();
    }

}
