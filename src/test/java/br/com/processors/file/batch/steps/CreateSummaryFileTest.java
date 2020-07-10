package br.com.processors.file.batch.steps;

import br.com.processors.file.batch.SpringBatchIntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static br.com.processors.file.TestConstants.*;

@RunWith(SpringRunner.class)
public class CreateSummaryFileTest extends SpringBatchIntegrationTest {
    private static final String STEP_NAME = "createSummaryFile";

    @Test
    @Sql("/sql/import-data.sql")
    public void testOneFileValid() throws IOException {
        final String jobId = "1";
        final JobParameters jobParams = new JobParametersBuilder()
            .addString(JOB_ID_PARAM, jobId).toJobParameters();

        assertCountTableRows(TABLE_CUSTOMERS, jobId, 4);
        assertCountTableRows(TABLE_SELLERS, jobId, 6);
        assertCountTableRows(TABLE_SALES, jobId, 2);
        assertCountTableRows(TABLE_SALE_ITEMS, jobId, 6);

        final JobExecution jobExecution = jobLauncherTestUtils.launchStep(STEP_NAME, jobParams);

        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);

        final Path outputFilePath = getOutputFilePath(jobId);
        assertThat(outputFilePath).exists();
        assertThat(getContentFile(outputFilePath)).isEqualTo("4ç6ç1çPedro");
    }
}
