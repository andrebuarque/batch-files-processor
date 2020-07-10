package br.com.processors.file.batch.steps;

import br.com.processors.file.models.SummarySales;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreateSummaryStep {
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private ItemReader<SummarySales> databaseItemReader;

    @Autowired
    private ItemWriter<SummarySales> saveSummaryFile;

    @Bean
    public Step createSummaryFile() {
        return stepBuilderFactory
            .get("createSummaryFile")
            .<SummarySales, SummarySales>chunk(1)
            .reader(databaseItemReader)
            .writer(saveSummaryFile)
            .build();
    }
}
