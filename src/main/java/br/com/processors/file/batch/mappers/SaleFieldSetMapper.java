package br.com.processors.file.batch.mappers;

import br.com.processors.file.config.InputFileConfigProperties;
import br.com.processors.file.config.PatternSaleItemsConfig;
import br.com.processors.file.models.FileItem;
import br.com.processors.file.models.Sale;
import br.com.processors.file.models.SaleItem;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@StepScope
public class SaleFieldSetMapper implements FieldSetMapper<FileItem> {
    private Pattern itemListPattern;

    private PatternSaleItemsConfig patternSaleItems;

    @Autowired
    private InputFileConfigProperties inputFileProperties;

    @Value("#{jobParameters['job.id']}")
    private String jobId;

    @PostConstruct
    public void init() {
        patternSaleItems = inputFileProperties.getPatternSaleItems();
        itemListPattern = Pattern.compile(patternSaleItems.getRegex());
    }

    @Override
    public Sale mapFieldSet(final FieldSet fieldSet) {
        final String id = fieldSet.readString("id");
        final String itemList = fieldSet.readString("itemList");
        final String seller = fieldSet.readString("seller");

        final Sale sale = new Sale();

        if (NumberUtils.isParsable(id)) {
            sale.setId(Long.parseLong(id));
        }

        sale.setJobId(Long.parseLong(jobId));
        sale.setSeller(seller);
        sale.setItems(mapItems(itemList));

        return sale;
    }

    private List<SaleItem> mapItems(final String itemList) {
        final ArrayList<SaleItem> items = new ArrayList<>();

        if (Strings.isBlank(itemList)) {
            return Collections.emptyList();
        }

        final Matcher matcher = itemListPattern.matcher(itemList);

        while (matcher.find()) {
            final String itemId = matcher.group(patternSaleItems.getGroupId());
            final String itemQuantity = matcher.group(patternSaleItems.getGroupQuantity());
            final String itemPrice = matcher.group(patternSaleItems.getGroupPrice());

            final SaleItem saleItem =
                new SaleItem(Long.parseLong(itemId), Integer.parseInt(itemQuantity), Float.parseFloat(itemPrice));

            items.add(saleItem);
        }

        return items;
    }
}
