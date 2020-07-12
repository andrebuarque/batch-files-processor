package br.com.processors.file.services;

import br.com.processors.file.models.SummarySales;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import static br.com.processors.file.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class SummarySalesReportServiceTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SummarySalesReportService summarySalesReportService;

    @AfterEach
    public void afterEachTest() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, TABLE_CUSTOMERS, TABLE_SELLERS, TABLE_SALE_ITEMS, TABLE_SALES);
    }

    @Test
    @Sql("/sql/import-data.sql")
    public void testReportWithData() {
        final SummarySales summarySales = summarySalesReportService.execute(1L);

        assertThat(summarySales).isNotNull();
        assertThat(summarySales.countSeller()).isEqualTo(6);
        assertThat(summarySales.countCustomer()).isEqualTo(4);
        assertThat(summarySales.biggestSaleId()).isEqualTo(1);
        assertThat(summarySales.worstSeller()).isEqualTo("Pedro");
    }

    @Test
    public void testReportWithoutData() {
        final SummarySales summarySales = summarySalesReportService.execute(1L);

        assertThat(summarySales).isNotNull();
        assertThat(summarySales.countSeller()).isZero();
        assertThat(summarySales.countCustomer()).isZero();
        assertThat(summarySales.biggestSaleId()).isZero();
        assertThat(summarySales.worstSeller()).isNull();
    }
}
