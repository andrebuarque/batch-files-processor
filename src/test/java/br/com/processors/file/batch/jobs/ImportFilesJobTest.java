package br.com.processors.file.batch.jobs;

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
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
class ImportFilesJobTest extends SpringBatchIntegrationTest {

    @BeforeAll
    public static void beforeAll() throws IOException {
        FileUtils.copyDirectory(Paths.get(INPUT_DIR).toFile(), Paths.get(PROCESSING_DIR).toFile());
    }

    @Test
    public void testEmptyFolder() throws Exception {
        final String filenameIn = new Object(){}.getClass().getEnclosingMethod().getName();

        final JobParameters jobParams = getJobParameters(filenameIn);
        final JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParams);

        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);
        assertThat(getOutputFilePath(jobParams.getString(JOB_ID_PARAM))).doesNotExist();
    }

    @Test
    public void testEmptyInputFile() throws Exception {
        final String filenameIn = new Object(){}.getClass().getEnclosingMethod().getName();

        final JobParameters jobParams = getJobParameters(filenameIn);

        final JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParams);

        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);
        assertThat(getOutputFilePath(jobParams.getString(JOB_ID_PARAM))).doesNotExist();
        assertThat(getProcessingFilePath(filenameIn)).doesNotExist();
    }

    @Test
    public void testInvalidFileContent() throws Exception {
        final String filenameIn = new Object(){}.getClass().getEnclosingMethod().getName();

        final JobParameters jobParams = getJobParameters(filenameIn);
        final JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParams);

        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);
        assertThat(getOutputFilePath(jobParams.getString(JOB_ID_PARAM))).doesNotExist();
        assertThat(getProcessingFilePath(filenameIn)).doesNotExist();
    }

    @Test
    public void testBinaryFile() throws Exception {
        final String filenameIn = new Object(){}.getClass().getEnclosingMethod().getName();

        final JobParameters jobParams = getJobParameters(filenameIn);
        final JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParams);

        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);
        assertThat(getOutputFilePath(jobParams.getString(JOB_ID_PARAM))).doesNotExist();
        assertThat(getProcessingFilePath(filenameIn)).doesNotExist();
    }

    @Test
    public void testOneFileValid() throws Exception {
        final String filenameIn = new Object(){}.getClass().getEnclosingMethod().getName();

        final JobParameters jobParams = getJobParameters(filenameIn);
        final JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParams);

        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);

        final Path outputFilePath = getOutputFilePath(jobParams.getString(JOB_ID_PARAM));
        assertThat(outputFilePath).exists();
        assertThat(getContentFile(outputFilePath)).isEqualTo("2ç2ç10çPaulo");
        assertThat(getProcessingFilePath(filenameIn)).doesNotExist();
    }

    @Test
    public void testOneFileWithSomeInvalidLines() throws Exception {
        final String filenameIn = new Object(){}.getClass().getEnclosingMethod().getName();

        final JobParameters jobParams = getJobParameters(filenameIn);
        final JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParams);

        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);

        final Path outputFilePath = getOutputFilePath(jobParams.getString(JOB_ID_PARAM));
        assertThat(outputFilePath).exists();
        assertThat(getContentFile(outputFilePath)).isEqualTo("2ç1ç8çAndre");
        assertThat(getProcessingFilePath(filenameIn)).doesNotExist();
    }

    @Test
    public void testOneFileWithMoreTokensThanExpected() throws Exception {
        final String filenameIn = new Object(){}.getClass().getEnclosingMethod().getName();

        final JobParameters jobParams = getJobParameters(filenameIn);
        final JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParams);

        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);

        final Path outputFilePath = getOutputFilePath(jobParams.getString(JOB_ID_PARAM));
        assertThat(outputFilePath).exists();
        assertThat(getContentFile(outputFilePath)).isEqualTo("2ç2ç280çAndre Buarque");
        assertThat(getProcessingFilePath(filenameIn)).doesNotExist();
    }

    @Test
    public void testMultipleValidFiles() throws Exception {
        final String filenameIn = new Object(){}.getClass().getEnclosingMethod().getName();

        final JobParameters jobParams = getJobParameters(filenameIn);
        final JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParams);

        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);

        final Path outputFilePath = getOutputFilePath(jobParams.getString(JOB_ID_PARAM));
        assertThat(outputFilePath).exists();
        assertThat(getContentFile(outputFilePath)).isEqualTo("22ç22ç10çPaulo");
        assertThat(getProcessingFilePath(filenameIn)).doesNotExist();
    }

    @Test
    public void testLargeValidFile() throws Exception {
        final String filenameIn = new Object(){}.getClass().getEnclosingMethod().getName();

        final JobParameters jobParams = getJobParameters(filenameIn);
        final JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParams);

        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);

        final Path outputFilePath = getOutputFilePath(jobParams.getString(JOB_ID_PARAM));
        assertThat(outputFilePath).exists();
        assertThat(getContentFile(outputFilePath)).isEqualTo("3335ç3334ç10çJuliano");
        assertThat(getProcessingFilePath(filenameIn)).doesNotExist();
    }

    @Test
    public void testFileWithSellersOnly() throws Exception {
        final String filenameIn = new Object(){}.getClass().getEnclosingMethod().getName();

        final JobParameters jobParams = getJobParameters(filenameIn);
        final JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParams);

        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);

        final Path outputFilePath = getOutputFilePath(jobParams.getString(JOB_ID_PARAM));
        assertThat(outputFilePath).exists();
        assertThat(getContentFile(outputFilePath)).isEqualTo("0ç25ç0ç");
        assertThat(getProcessingFilePath(filenameIn)).doesNotExist();
    }

    @Test
    public void testFileWithCustomersOnly() throws Exception {
        final String filenameIn = new Object(){}.getClass().getEnclosingMethod().getName();

        final JobParameters jobParams = getJobParameters(filenameIn);
        final JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParams);

        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);

        final Path outputFilePath = getOutputFilePath(jobParams.getString(JOB_ID_PARAM));
        assertThat(outputFilePath).exists();
        assertThat(getContentFile(outputFilePath)).isEqualTo("25ç0ç0ç");
        assertThat(getProcessingFilePath(filenameIn)).doesNotExist();
    }

    @Test
    public void testFileWithSalesOnly() throws Exception {
        final String filenameIn = new Object(){}.getClass().getEnclosingMethod().getName();

        final JobParameters jobParams = getJobParameters(filenameIn);
        final JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParams);

        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);

        final Path outputFilePath = getOutputFilePath(jobParams.getString(JOB_ID_PARAM));
        assertThat(outputFilePath).exists();
        assertThat(getContentFile(outputFilePath)).isEqualTo("0ç0ç128çAndre");
        assertThat(getProcessingFilePath(filenameIn)).doesNotExist();
    }
}
