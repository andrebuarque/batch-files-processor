package br.com.processors.file.services;

import br.com.processors.file.dao.CustomerDAO;
import br.com.processors.file.dao.SaleDAO;
import br.com.processors.file.dao.SellerDAO;
import br.com.processors.file.models.SummarySales;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SummarySalesReportServiceImpl implements SummarySalesReportService {
    @Autowired
    private CustomerDAO customerDAO;

    @Autowired
    private SellerDAO sellerDAO;

    @Autowired
    private SaleDAO saleDAO;

    @Override
    public SummarySales execute(final String jobId) {
        final Long countCustomer = customerDAO.getCountByJobId(jobId);
        final Long countSeller = sellerDAO.getCountByJobId(jobId);
        final Long biggestSaleId = saleDAO.getBiggestSaleIdByJobId(jobId);
        final String worstSeller = saleDAO.getWorstSellerByJobId(jobId);

        return new SummarySales(biggestSaleId, worstSeller, countSeller, countCustomer);
    }
}
