package br.com.processors.file.batch.tokenizers;

import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;

public class SellerTokenizer extends DelimitedLineTokenizer {
    public SellerTokenizer(final String delimiter) {
        setDelimiter(delimiter);
        setNames("document", "name", "salary");
        setIncludedFields(1, 2, 3);
    }
}
