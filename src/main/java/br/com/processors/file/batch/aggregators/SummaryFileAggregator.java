package br.com.processors.file.batch.aggregators;

import br.com.processors.file.config.OutputFileConfigProperties;
import br.com.processors.file.models.SummarySales;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
public class SummaryFileAggregator implements LineAggregator<SummarySales> {
    @Autowired
    private OutputFileConfigProperties configProperties;

    @Override
    public String aggregate(final SummarySales summarySales) {
        final String delimiter = configProperties.getDelimiter();

        final List<String> reportData = Arrays.asList(
            Objects.toString(summarySales.countCustomer(), ""),
            Objects.toString(summarySales.countSeller(), ""),
            Objects.toString(summarySales.biggestSaleId(), ""),
            Objects.toString(summarySales.worstSeller(), "")
        );

        return String.join(delimiter, reportData);
    }
}
