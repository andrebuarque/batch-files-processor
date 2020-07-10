package br.com.processors.file.batch.steps;

import br.com.processors.file.batch.SpringBatchIntegrationTest;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static br.com.processors.file.TestConstants.*;

@RunWith(SpringRunner.class)
public class ReadFilesStepTest extends SpringBatchIntegrationTest {
    private static final String STEP_NAME = "readFiles";

    @BeforeAll
    public static void beforeAll() throws IOException {
        FileUtils.copyDirectory(Paths.get(INPUT_DIR).toFile(), Paths.get(PROCESSING_DIR).toFile());
    }

    public void assertIsFailOnReading(final String filenameIn) {
        final JobParameters jobParams = getJobParameters(filenameIn);
        final String jobId = jobParams.getString(JOB_ID_PARAM);

        assertIsEmptyTables(jobId);
        final JobExecution jobExecution = jobLauncherTestUtils.launchStep(STEP_NAME, jobParams);
        assertIsEmptyTables(jobId);

        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.FAILED);
    }

    @Test
    public void testEmptyFolder() {
        assertIsFailOnReading("testEmptyFolder");
    }

    @Test
    public void testEmptyInputFile() {
        assertIsFailOnReading("testEmptyInputFile");
    }

    @Test
    public void testInvalidFileContent() {
        assertIsFailOnReading("testInvalidFileContent");
    }

    @Test
    public void testOneFileValid() {
        final String filenameIn = new Object(){}.getClass().getEnclosingMethod().getName();
        final JobParameters jobParams = getJobParameters(filenameIn);
        final String jobId = jobParams.getString(JOB_ID_PARAM);

        assertIsEmptyTables(jobId);

        final JobExecution jobExecution = jobLauncherTestUtils.launchStep(STEP_NAME, jobParams);

        assertCountTableRows(TABLE_CUSTOMERS, jobId, 2);
        assertCountTableRows(TABLE_SELLERS, jobId, 2);
        assertCountTableRows(TABLE_SALES, jobId, 2);

        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);
    }

    @Test
    public void testOneFileWithSomeInvalidLines() {
        final String filenameIn = new Object(){}.getClass().getEnclosingMethod().getName();
        final JobParameters jobParams = getJobParameters(filenameIn);
        final String jobId = jobParams.getString(JOB_ID_PARAM);

        assertIsEmptyTables(jobId);

        final JobExecution jobExecution = jobLauncherTestUtils.launchStep(STEP_NAME, jobParams);

        assertCountTableRows(TABLE_CUSTOMERS, jobId, 2);
        assertCountTableRows(TABLE_SELLERS, jobId, 1);
        assertCountTableRows(TABLE_SALES, jobId, 1);

        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);
    }

    @Test
    public void testOneFileWithMoreTokensThanExpected() {
        final String filenameIn = new Object(){}.getClass().getEnclosingMethod().getName();
        final JobParameters jobParams = getJobParameters(filenameIn);
        final String jobId = jobParams.getString(JOB_ID_PARAM);

        assertIsEmptyTables(jobId);

        final JobExecution jobExecution = jobLauncherTestUtils.launchStep(STEP_NAME, jobParams);

        assertCountTableRows(TABLE_CUSTOMERS, jobId, 2);
        assertCountTableRows(TABLE_SELLERS, jobId, 2);
        assertCountTableRows(TABLE_SALES, jobId, 2);

        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);
    }

    @Test
    public void testMultipleValidFiles() {
        final String filenameIn = new Object(){}.getClass().getEnclosingMethod().getName();
        final JobParameters jobParams = getJobParameters(filenameIn);
        final String jobId = jobParams.getString(JOB_ID_PARAM);

        assertIsEmptyTables(jobId);

        final JobExecution jobExecution = jobLauncherTestUtils.launchStep(STEP_NAME, jobParams);

        assertCountTableRows(TABLE_CUSTOMERS, jobId, 22);
        assertCountTableRows(TABLE_SELLERS, jobId, 22);
        assertCountTableRows(TABLE_SALES, jobId, 22);

        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);
    }

    @Test
    public void testLargeValidFile() {
        final String filenameIn = new Object(){}.getClass().getEnclosingMethod().getName();
        final JobParameters jobParams = getJobParameters(filenameIn);
        final String jobId = jobParams.getString(JOB_ID_PARAM);

        assertIsEmptyTables(jobId);

        final JobExecution jobExecution = jobLauncherTestUtils.launchStep(STEP_NAME, jobParams);

        assertCountTableRows(TABLE_CUSTOMERS, jobId, 3335);
        assertCountTableRows(TABLE_SELLERS, jobId, 3334);
        assertCountTableRows(TABLE_SALES, jobId, 3332);

        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);
    }

    @Test
    public void testFileWithSellersOnly() {
        final String filenameIn = new Object(){}.getClass().getEnclosingMethod().getName();
        final JobParameters jobParams = getJobParameters(filenameIn);
        final String jobId = jobParams.getString(JOB_ID_PARAM);

        assertIsEmptyTables(jobId);

        final JobExecution jobExecution = jobLauncherTestUtils.launchStep(STEP_NAME, jobParams);

        assertCountTableRows(TABLE_CUSTOMERS, jobId, 0);
        assertCountTableRows(TABLE_SELLERS, jobId, 25);
        assertCountTableRows(TABLE_SALES, jobId, 0);

        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);
    }

    @Test
    public void testFileWithCustomersOnly() {
        final String filenameIn = new Object(){}.getClass().getEnclosingMethod().getName();
        final JobParameters jobParams = getJobParameters(filenameIn);
        final String jobId = jobParams.getString(JOB_ID_PARAM);

        assertIsEmptyTables(jobId);

        final JobExecution jobExecution = jobLauncherTestUtils.launchStep(STEP_NAME, jobParams);

        assertCountTableRows(TABLE_CUSTOMERS, jobId, 25);
        assertCountTableRows(TABLE_SELLERS, jobId, 0);
        assertCountTableRows(TABLE_SALES, jobId, 0);

        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);
    }

    @Test
    public void testFileWithSalesOnly() {
        final String filenameIn = new Object(){}.getClass().getEnclosingMethod().getName();
        final JobParameters jobParams = getJobParameters(filenameIn);
        final String jobId = jobParams.getString(JOB_ID_PARAM);

        assertIsEmptyTables(jobId);

        final JobExecution jobExecution = jobLauncherTestUtils.launchStep(STEP_NAME, jobParams);

        assertCountTableRows(TABLE_CUSTOMERS, jobId, 0);
        assertCountTableRows(TABLE_SELLERS, jobId, 0);
        assertCountTableRows(TABLE_SALES, jobId, 24);

        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);
    }
}
