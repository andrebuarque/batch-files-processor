package br.com.processors.file.batch.steps;

import br.com.processors.file.batch.policies.ItemSkipPolicy;
import br.com.processors.file.config.InputFileConfigProperties;
import br.com.processors.file.models.FileItem;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.listener.ExecutionContextPromotionListener;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReadFilesStep {
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private InputFileConfigProperties inputFileProperties;

    @Autowired
    private ItemReader<FileItem> multiFileReader;

    @Autowired
    private ValidatingItemProcessor<FileItem> itemValidatingProcessor;

    @Autowired
    private ItemWriter<FileItem> databaseItemWriter;

    @Autowired
    private StepExecutionListener readFilesListener;

    @Bean
    public Step readFiles() {
        return this.stepBuilderFactory
            .get("readFiles")
            .<FileItem, FileItem>chunk(inputFileProperties.getChunkSize())
            .faultTolerant()
            .skipPolicy(new ItemSkipPolicy())
            .reader(multiFileReader)
            .processor(itemValidatingProcessor)
            .writer(databaseItemWriter)
            .listener(readFilesListener)
            .listener(promotionListener())
            .build();
    }

    @Bean
    public ExecutionContextPromotionListener promotionListener() {
        final ExecutionContextPromotionListener listener = new ExecutionContextPromotionListener();

        listener.setKeys(new String[] { "input.files" });

        return listener;
    }
}
