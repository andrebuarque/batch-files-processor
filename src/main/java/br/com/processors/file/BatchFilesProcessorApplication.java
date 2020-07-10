package br.com.processors.file;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class BatchFilesProcessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatchFilesProcessorApplication.class, args);
	}

}
