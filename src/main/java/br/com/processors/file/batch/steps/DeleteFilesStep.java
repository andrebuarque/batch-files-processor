package br.com.processors.file.batch.steps;

import br.com.processors.file.batch.tasklets.FileDeletingTasklet;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeleteFilesStep {
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step deleteFiles() {
        return stepBuilderFactory
            .get("deleteFile")
            .tasklet(new FileDeletingTasklet())
            .build();
    }
}
