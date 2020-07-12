package br.com.processors.file.batch.readers;

import br.com.processors.file.models.SummarySales;
import br.com.processors.file.services.SummarySalesReportService;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@StepScope
public class DatabaseItemReader implements ItemReader<SummarySales> {
    private SummarySales summarySales;

    @Value("#{jobParameters['job.id']}")
    private String jobId;

    @Autowired
    SummarySalesReportService summaryReportService;

    @Override
    public SummarySales read() {
        if (Objects.isNull(summarySales)) {
            summarySales = summaryReportService.execute(Long.parseLong(jobId));
            return summarySales;
        }

        return null;
    }
}
