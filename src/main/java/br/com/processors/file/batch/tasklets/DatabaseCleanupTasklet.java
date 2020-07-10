package br.com.processors.file.batch.tasklets;

import br.com.processors.file.dao.CustomerDAO;
import br.com.processors.file.dao.SaleDAO;
import br.com.processors.file.dao.SellerDAO;
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
    private String jobId;

    @Autowired
    private CustomerDAO customerDAO;

    @Autowired
    private SellerDAO sellerDAO;

    @Autowired
    private SaleDAO saleDAO;

    @Override
    public RepeatStatus execute(final StepContribution stepContribution,
                                final ChunkContext chunkContext) throws Exception {
        customerDAO.deleteAllByJobId(jobId);
        sellerDAO.deleteAllByJobId(jobId);
        saleDAO.deleteAllByJobId(jobId);

        return RepeatStatus.FINISHED;
    }
}
