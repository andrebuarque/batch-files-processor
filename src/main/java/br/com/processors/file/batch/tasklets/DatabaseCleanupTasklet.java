package br.com.processors.file.batch.tasklets;

import br.com.processors.file.repositories.CustomerRepository;
import br.com.processors.file.repositories.SaleRepository;
import br.com.processors.file.repositories.SellerRepository;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class DatabaseCleanupTasklet implements Tasklet {
    @Value("#{jobParameters['job.id']}")
    private String jobIdParam;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private SaleRepository saleRepository;

    @Override
    public RepeatStatus execute(final StepContribution stepContribution,
                                final ChunkContext chunkContext) {
        final long jobId = Long.parseLong(jobIdParam);

        customerRepository.deleteByJobId(jobId);
        sellerRepository.deleteByJobId(jobId);
        saleRepository.deleteByJobId(jobId);

        return RepeatStatus.FINISHED;
    }
}
