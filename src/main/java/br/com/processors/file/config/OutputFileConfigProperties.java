package br.com.processors.file.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "output.file")
public class OutputFileConfigProperties {
    private String prefix;
    private String suffix;
    private String directory;
    private String delimiter;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(final String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(final String suffix) {
        this.suffix = suffix;
    }

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
}
