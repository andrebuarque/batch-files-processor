package br.com.processors.file.batch.tasklets;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class FileDeletingTasklet implements Tasklet {
    @Override
    @SuppressWarnings("unchecked")
    public RepeatStatus execute(final StepContribution stepContribution,
                                final ChunkContext chunkContext) {
        final Map<String, Object> context = chunkContext.getStepContext().getJobExecutionContext();
        final List<URI> uris = (List<URI>) context.get("input.files");

        uris.stream()
            .map(Paths::get)
            .forEach(this::deleteFile);

        return RepeatStatus.FINISHED;
    }

    private void deleteFile(final Path path) {
        try {
            Files.delete(path);
        } catch (final IOException ignored) {

        }
    }
}
