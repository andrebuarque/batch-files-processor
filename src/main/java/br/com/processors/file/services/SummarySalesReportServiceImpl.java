package br.com.processors.file.services;

import br.com.processors.file.models.Sale;
import br.com.processors.file.models.SummarySales;
import br.com.processors.file.repositories.CustomerRepository;
import br.com.processors.file.repositories.SaleRepository;
import br.com.processors.file.repositories.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SummarySalesReportServiceImpl implements SummarySalesReportService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private SaleRepository saleRepository;

    @Override
    public SummarySales execute(final Long jobId) {
        final Long countCustomer = customerRepository.countByJobId(jobId);
        final Long countSeller = sellerRepository.countByJobId(jobId);

        final List<Sale> sales = saleRepository.findByJobId(jobId);
        final Long biggestSaleId = getBiggestSaleId(sales);
        final String worstSeller = getWorstSellerByJobId(sales);

        return new SummarySales(biggestSaleId, worstSeller, countSeller, countCustomer);
    }

    private Long getBiggestSaleId(final List<Sale> sales) {
        return sales.stream()
            .collect(
                Collectors.groupingBy(
                    Sale::getSaleId,
                    Collectors.summingDouble(Sale::getTotal)
                )
            )
            .entrySet().stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .map(Map.Entry::getKey)
            .findFirst()
            .orElse(0L);
    }

    private String getWorstSellerByJobId(final List<Sale> sales) {
        return sales.stream()
            .collect(
                Collectors.groupingBy(
                    Sale::getSeller,
                    Collectors.summingDouble(Sale::getTotal)
                )
            )
            .entrySet().stream()
            .sorted(Map.Entry.comparingByValue(Comparator.naturalOrder()))
            .map(Map.Entry::getKey)
            .findFirst()
            .orElse(null);
    }
}
