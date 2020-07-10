package br.com.processors.file.batch.tokenizers;

import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;

public class CustomerTokenizer extends DelimitedLineTokenizer {
    public CustomerTokenizer(final String delimiter) {
        setDelimiter(delimiter);
        setNames("document", "name", "businessArea");
        setIncludedFields(1, 2, 3);
    }
}
