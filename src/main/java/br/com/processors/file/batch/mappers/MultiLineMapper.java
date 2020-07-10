package br.com.processors.file.batch.mappers;

import br.com.processors.file.batch.tokenizers.CustomerTokenizer;
import br.com.processors.file.batch.tokenizers.SaleTokenizer;
import br.com.processors.file.batch.tokenizers.SellerTokenizer;
import br.com.processors.file.config.InputFileConfigProperties;
import br.com.processors.file.models.FileItem;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.mapping.PatternMatchingCompositeLineMapper;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MultiLineMapper {
    public static final String LINE_SELLER_PATTERN = "001%s*";
    public static final String LINE_CUSTOMER_PATTERN = "002%s*";
    public static final String LINE_SALE_PATTERN = "003%s*";

    @Autowired
    private InputFileConfigProperties inputFileProperties;

    @Autowired
    private SaleFieldSetMapper saleFieldSetMapper;

    @Autowired
    private CustomerFieldSetMapper customerFieldSetMapper;

    @Autowired
    private SellerFieldSetMapper sellerFieldSetMapper;

    @Bean
    public PatternMatchingCompositeLineMapper<FileItem> compositeLineMapper() {
        final PatternMatchingCompositeLineMapper<FileItem> lineMapper = new PatternMatchingCompositeLineMapper<>();
        final String inputFileDelimiter = inputFileProperties.getDelimiter();

        final String lineSellerPattern = String.format(LINE_SELLER_PATTERN, inputFileDelimiter);
        final String lineCustomerPattern = String.format(LINE_CUSTOMER_PATTERN, inputFileDelimiter);
        final String lineSalePattern = String.format(LINE_SALE_PATTERN, inputFileDelimiter);

        final Map<String, LineTokenizer> tokenizers = new HashMap<>();
        tokenizers.put(lineSellerPattern, new SellerTokenizer(inputFileDelimiter));
        tokenizers.put(lineCustomerPattern, new CustomerTokenizer(inputFileDelimiter));
        tokenizers.put(lineSalePattern, new SaleTokenizer(inputFileDelimiter));

        final Map<String, FieldSetMapper<FileItem>> mappers = new HashMap<>();
        mappers.put(lineSellerPattern, sellerFieldSetMapper);
        mappers.put(lineCustomerPattern, customerFieldSetMapper);
        mappers.put(lineSalePattern, saleFieldSetMapper);

        lineMapper.setTokenizers(tokenizers);
        lineMapper.setFieldSetMappers(mappers);

        return lineMapper;
    }
}
