package br.com.ms.produto.batch.writers;

import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.data.jpa.repository.JpaRepository;

public class RepositoryWriter<T, S> extends RepositoryItemWriter<T> {

    public RepositoryWriter(JpaRepository<T, S> repository) {
        super.setRepository(repository);
    }

    public RepositoryWriter<T, S> build() {
        return this;
    }

}
