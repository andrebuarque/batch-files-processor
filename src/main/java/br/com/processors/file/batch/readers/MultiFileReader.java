package br.com.processors.file.batch.readers;

import br.com.processors.file.models.FileItem;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
@StepScope
public class MultiFileReader extends MultiResourceItemReader<FileItem> {
    private Resource[] resources;

    @Value("#{jobParameters['input.files.dir']}")
    private String inputFilesDir;

    @Autowired
    private FlatFileItemReader<FileItem> flatFileItemReader;

    public MultiFileReader() {
        super();
    }

    @PostConstruct
    public void init() throws IOException {
        resources = new PathMatchingResourcePatternResolver().getResources(inputFilesDir);
        this.setResources(resources);
        this.setDelegate(flatFileItemReader);
    }

    @BeforeStep
    public void beforeStep(final StepExecution stepExecution) throws IOException {
        final List<URI> filesURI = new ArrayList<>();

        Arrays.stream(resources)
            .map(this::getURI)
            .filter(Objects::nonNull)
            .forEach(filesURI::add);

        stepExecution.getExecutionContext().put("input.files", filesURI);
    }

    private URI getURI(final Resource resource) {
        try {
            return resource.getURI();
        } catch (final IOException e) {
            return null;
        }
    }
}
