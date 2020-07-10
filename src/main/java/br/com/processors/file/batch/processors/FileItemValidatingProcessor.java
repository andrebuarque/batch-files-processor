package br.com.processors.file.batch.processors;

import br.com.processors.file.models.FileItem;
import org.springframework.batch.item.validator.BeanValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileItemValidatingProcessor {
    @Bean
    public ValidatingItemProcessor<FileItem> itemValidatingProcessor() {
        final BeanValidatingItemProcessor<FileItem> validatingItemProcessor =
            new BeanValidatingItemProcessor<>();
        validatingItemProcessor.setFilter(true);

        return validatingItemProcessor;
    }
}
