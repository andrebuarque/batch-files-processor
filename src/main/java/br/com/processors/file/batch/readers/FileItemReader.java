package br.com.processors.file.batch.readers;

import br.com.processors.file.config.InputFileConfigProperties;
import br.com.processors.file.models.FileItem;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class FileItemReader {
    private Resource[] resources;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private LineMapper<FileItem> compositeLineMapper;

    @Autowired
    private InputFileConfigProperties inputFileProperties;

    @Bean
    public FlatFileItemReader<FileItem> flatFileItemReader() {
        final FlatFileItemReader<FileItem> flatFileReader = new FlatFileItemReader<>();

        flatFileReader.setStrict(false);
        flatFileReader.setEncoding("UTF-8");
        flatFileReader.setLineMapper(compositeLineMapper);

        return flatFileReader;
    }
}
