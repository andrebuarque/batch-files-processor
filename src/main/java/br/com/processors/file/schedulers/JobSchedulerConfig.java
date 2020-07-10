package br.com.processors.file.schedulers;

import br.com.processors.file.config.InputFileConfigProperties;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@Profile("!test")
public class JobSchedulerConfig {
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job importFiles;

    @Autowired
    private InputFileConfigProperties inputFileProperties;

    @Scheduled(fixedRateString = "${scheduler.rate}")
    public void perform() throws Exception {
        final String inputFilesDir = inputFileProperties.getDirectory();
        final Resource[] resources = new PathMatchingResourcePatternResolver().getResources(inputFilesDir);

        if (resources.length == 0) {
            return;
        }

        final JobParameters jobParams = new JobParametersBuilder()
            .addString("job.id", String.valueOf(System.currentTimeMillis()))
            .addString("input.files.dir", inputFilesDir)
            .toJobParameters();

        jobLauncher.run(importFiles, jobParams);
    }
}
