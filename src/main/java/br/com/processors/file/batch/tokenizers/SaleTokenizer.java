package br.com.processors.file.batch.tokenizers;

import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;

public class SaleTokenizer extends DelimitedLineTokenizer {
    public SaleTokenizer(final String delimiter) {
        setDelimiter(delimiter);
        setNames("id", "itemList", "seller");
        setIncludedFields(1, 2, 3);
    }
}
