package br.com.processors.file.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "input.file")
public class InputFileConfigProperties {
    private String directory;
    private String delimiter;
    private Integer chunkSize;
    private PatternSaleItemsConfig patternSaleItems;

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(final String directory) {
        this.directory = directory;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(final String delimiter) {
        this.delimiter = delimiter;
    }

    public Integer getChunkSize() {
        return chunkSize;
    }

    public void setChunkSize(final Integer chunkSize) {
        this.chunkSize = chunkSize;
    }

    public PatternSaleItemsConfig getPatternSaleItems() {
        return patternSaleItems;
    }

    public void setPatternSaleItems(final PatternSaleItemsConfig patternSaleItems) {
        this.patternSaleItems = patternSaleItems;
    }
}
