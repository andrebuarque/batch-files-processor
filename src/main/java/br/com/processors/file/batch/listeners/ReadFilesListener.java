package br.com.processors.file.batch.listeners;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class ReadFilesListener implements StepExecutionListener {
    @Override
    public void beforeStep(final StepExecution stepExecution) {

    }

    @Override
    public ExitStatus afterStep(final StepExecution stepExecution) {
        final int readCount = stepExecution.getReadCount();

        if (readCount == 0) {
            return ExitStatus.FAILED;
        }

        return null;
    }
}
