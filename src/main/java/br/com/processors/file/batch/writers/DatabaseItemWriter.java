package br.com.processors.file.batch.writers;

import br.com.processors.file.models.Customer;
import br.com.processors.file.models.Sale;
import br.com.processors.file.models.Seller;
import br.com.processors.file.repositories.CustomerRepository;
import br.com.processors.file.repositories.SaleRepository;
import br.com.processors.file.repositories.SellerRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DatabaseItemWriter<FileItem> implements ItemWriter<FileItem> {
    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    @SuppressWarnings("unchecked")
    public void write(final List<? extends FileItem> items) throws Exception {
        final List<Seller> sellers = (List<Seller>) getItemsByClass(Seller.class, items);
        final List<Customer> customers = (List<Customer>) getItemsByClass(Customer.class, items);
        final List<Sale> sales = (List<Sale>) getItemsByClass(Sale.class, items);

        sellerRepository.saveAll(sellers);
        customerRepository.saveAll(customers);
        saleRepository.saveAll(sales);
    }

    private List<FileItem> getItemsByClass(final Class<?> clazz, final List<? extends FileItem> items) {
        return items.stream()
            .filter(clazz::isInstance)
            .collect(Collectors.toList());
    }
}
