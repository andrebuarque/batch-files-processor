package br.com.processors.file.batch.writers;

import br.com.processors.file.config.OutputFileConfigProperties;
import br.com.processors.file.models.SummarySales;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class SaveSummaryFileWriter {
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private OutputFileConfigProperties configProperties;

    @Autowired
    private LineAggregator<SummarySales> summaryFileAggregator;

    @Bean
    @StepScope
    public FlatFileItemWriter<SummarySales> saveSummaryFile(@Value("#{jobParameters['job.id']}") final String jobId) {
        final String prefix = configProperties.getPrefix();
        final String suffix = configProperties.getSuffix();
        final String directory = configProperties.getDirectory();

        final String filename = String.format("%s_%s%s", prefix, jobId, suffix);
        final Path destinationFilePath = Paths.get(directory, filename);

        final FlatFileItemWriter<SummarySales> writer = new FlatFileItemWriter<>();
        writer.setResource(new FileSystemResource(destinationFilePath));
        writer.setLineAggregator(summaryFileAggregator);

        return writer;
    }
}
