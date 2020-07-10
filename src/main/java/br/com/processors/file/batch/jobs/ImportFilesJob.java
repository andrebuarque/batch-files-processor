package br.com.processors.file.batch.jobs;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImportFilesJob {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private Step readFiles;

    @Autowired
    private Step createSummaryFile;

    @Autowired
    private Step deleteFiles;

    @Autowired
    private Step databaseCleanup;

    @Bean
    public Job importFiles() {
        return jobBuilderFactory
            .get("importFiles")
            .preventRestart()
            .start(readFiles).on("FAILED").to(deleteFiles)
            .from(readFiles).on("COMPLETED").to(createSummaryFile)
            .from(createSummaryFile).on("*").to(databaseCleanup)
            .from(databaseCleanup).on("*").to(deleteFiles)
            .end()
            .build();
    }
}
