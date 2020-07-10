package br.com.processors.file.dao;

import br.com.processors.file.models.Sale;
import br.com.processors.file.models.SaleItem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.util.ArrayList;

import static br.com.processors.file.TestConstants.TABLE_SALES;
import static br.com.processors.file.TestConstants.TABLE_SALE_ITEMS;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class SaleDAOTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SaleDAO saleDAO;

    @AfterEach
    public void afterEachTest() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, TABLE_SALES, TABLE_SALE_ITEMS);
    }

    @Test
    @Sql("/sql/import-sales.sql")
    public void testDeleteAllByJob() {
        saleDAO.deleteAllByJobId("1");

        final int countRowsInSales = JdbcTestUtils.countRowsInTable(jdbcTemplate, TABLE_SALES);
        final int countRowsInSaleItems = JdbcTestUtils.countRowsInTable(jdbcTemplate, TABLE_SALE_ITEMS);

        assertThat(countRowsInSales).isZero();
        assertThat(countRowsInSaleItems).isZero();
    }

    @Test
    public void testDeleteAllByJobWithoutData() {
        saleDAO.deleteAllByJobId("1");

        final int countRowsInSales = JdbcTestUtils.countRowsInTable(jdbcTemplate, TABLE_SALES);
        final int countRowsInSaleItems = JdbcTestUtils.countRowsInTable(jdbcTemplate, TABLE_SALE_ITEMS);

        assertThat(countRowsInSales).isZero();
        assertThat(countRowsInSaleItems).isZero();
    }

    @Test
    public void testSaveAll() {
        final ArrayList<SaleItem> saleItems = new ArrayList<>();
        saleItems.add(new SaleItem(1L, 1, 1.5F));
        saleItems.add(new SaleItem(2L, 2, 2.5F));
        saleItems.add(new SaleItem(2L, 3, 3.5F));

        final Sale sale = new Sale();
        sale.setId(1L);
        sale.setJobId(1L);
        sale.setSeller("seller");
        sale.setItems(saleItems);

        final ArrayList<Sale> sales = new ArrayList<>();
        final int countSales = 20;
        final int countSaleItems = countSales * saleItems.size();

        for (int i = 0; i < countSales; i++) {
            sales.add(sale);
        }

        saleDAO.saveAll(sales);

        final int countRowsInSales = JdbcTestUtils.countRowsInTable(jdbcTemplate, TABLE_SALES);
        final int countRowsInSaleItems = JdbcTestUtils.countRowsInTable(jdbcTemplate, TABLE_SALE_ITEMS);

        assertThat(countRowsInSales).isEqualTo(countSales);
        assertThat(countRowsInSaleItems).isEqualTo(countSaleItems);
    }

    @Test
    public void testSaveAllWithNullValues() {
        final ArrayList<SaleItem> saleItems = new ArrayList<>();
        saleItems.add(new SaleItem(null, 1, 1.5F));

        final Sale sale = new Sale();
        sale.setId(null);
        sale.setJobId(null);
        sale.setSeller(null);
        sale.setItems(saleItems);

        final ArrayList<Sale> sales = new ArrayList<>();
        sales.add(sale);

        Assertions.assertThrows(NullPointerException.class, () -> {
            saleDAO.saveAll(sales);
        });
    }

    @Test
    public void testSaveAllPassingNull() {
        saleDAO.saveAll(null);

        final int countRowsInSales = JdbcTestUtils.countRowsInTable(jdbcTemplate, TABLE_SALES);
        final int countRowsInSaleItems = JdbcTestUtils.countRowsInTable(jdbcTemplate, TABLE_SALE_ITEMS);

        assertThat(countRowsInSales).isZero();
        assertThat(countRowsInSaleItems).isZero();
    }

    @Test
    @Sql("/sql/import-sales.sql")
    public void testBiggestSaleId() {
        final Long biggestSaleId = saleDAO.getBiggestSaleIdByJobId("1");

        assertThat(biggestSaleId).isEqualTo(1);
    }

    @Test
    public void testBiggestSaleIdWithoutData() {
        final Long biggestSaleId = saleDAO.getBiggestSaleIdByJobId("1");

        assertThat(biggestSaleId).isZero();
    }

    @Test
    @Sql("/sql/import-sales.sql")
    public void testWorstSeller() {
        final String worstSeller = saleDAO.getWorstSellerByJobId("1");

        assertThat(worstSeller).isEqualTo("Pedro");
    }

    @Test
    public void testWorstSellerWithoutData() {
        final String worstSeller = saleDAO.getWorstSellerByJobId("1");

        assertThat(worstSeller).isNull();
    }
}
