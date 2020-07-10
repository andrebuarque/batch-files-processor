package br.com.processors.file.batch.steps;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseCleanupStep {
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private Tasklet databaseCleanupTasklet;

    @Bean
    public Step databaseCleanup() {
        return stepBuilderFactory
            .get("databaseCleanup")
            .tasklet(databaseCleanupTasklet)
            .build();
    }

}
