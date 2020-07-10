package br.com.processors.file.batch.policies;

import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;

public class ItemSkipPolicy implements SkipPolicy {
    public boolean shouldSkip(final Throwable throwable, final int skipCount) throws SkipLimitExceededException {
        return true;
    }
}
