package br.com.processors.file.batch;

import br.com.processors.file.BatchFilesProcessorApplication;
import br.com.processors.file.config.OutputFileConfigProperties;
import org.junit.jupiter.api.AfterAll;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static br.com.processors.file.TestConstants.*;

@SpringBatchTest
@SpringBootTest
@ContextConfiguration(classes = { BatchFilesProcessorApplication.class })
@ActiveProfiles("test")
public class SpringBatchIntegrationTest {
    @Autowired
    protected JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    protected OutputFileConfigProperties outputConfigProperties;

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    protected String outputPrefix;
    protected String outputSuffix;

    protected static final String JOB_ID_PARAM = "job.id";
    protected static final String FILE_EXTENSION = "*.dat";
    protected static final String USER_DIR = System.getProperty("user.dir");
    protected static final String INPUT_DIR = USER_DIR + "/src/test/resources/files/in/";
    protected static final String PROCESSING_DIR = USER_DIR + "/src/test/resources/files/processing/";
    protected static String outputDir;

    @PostConstruct
    public void init() {
        outputPrefix = outputConfigProperties.getPrefix();
        outputSuffix = outputConfigProperties.getSuffix();
        outputDir = outputConfigProperties.getDirectory();
    }

    @AfterAll
    public static void afterAllTest() throws IOException {
        Files.walk(Paths.get(PROCESSING_DIR))
            .filter(Files::isRegularFile)
            .map(Path::toFile)
            .forEach(File::delete);

        Files.walk(Paths.get(outputDir))
            .filter(Files::isRegularFile)
            .map(Path::toFile)
            .forEach(File::delete);
    }

    protected String getInputFileDir(final String filename) {
        return "file://" + PROCESSING_DIR + filename + FILE_EXTENSION;
    }

    protected String getContentFile(final Path path) throws IOException {
        return Files.lines(path).findFirst().get();
    }

    protected Path getOutputFilePath(final String jobId) {
        final String filename = String.format("%s_%s%s", outputPrefix, jobId, outputSuffix);
        return Paths.get(outputDir, filename);
    }

    protected Path getProcessingFilePath(final String filename) {
        return Paths.get(PROCESSING_DIR, filename + FILE_EXTENSION);
    }

    protected JobParameters getJobParameters(final String filenameIn) {
        final String jobId = String.valueOf(System.currentTimeMillis());

        return new JobParametersBuilder()
            .addString(JOB_ID_PARAM, jobId)
            .addString("input.files.dir", getInputFileDir(filenameIn))
            .toJobParameters();
    }

    protected int countRowsInTableWhere(final String table, final String jobId) {
        return JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, table, "job_id=" + jobId);
    }

    protected void assertCountTableRows(final String table, final String jobId, final int expected) {
        final int rows = countRowsInTableWhere(table, jobId);
        assertThat(rows).isEqualTo(expected);
    }

    protected void assertIsEmptyTables(final String jobId) {
        final int countCustomers = countRowsInTableWhere(TABLE_CUSTOMERS, jobId);
        final int countSellers = countRowsInTableWhere(TABLE_SELLERS, jobId);
        final int countSales = countRowsInTableWhere(TABLE_SALES, jobId);
        final int countSaleItems = countRowsInTableWhere(TABLE_SALE_ITEMS, jobId);

        assertThat(countCustomers).isZero();
        assertThat(countSellers).isZero();
        assertThat(countSales).isZero();
        assertThat(countSaleItems).isZero();
    }
}
