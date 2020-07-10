package br.com.processors.file.dao;

import br.com.processors.file.models.Seller;
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

import static br.com.processors.file.TestConstants.TABLE_SELLERS;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class SellerDAOTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SellerDAO sellerDAO;

    @AfterEach
    public void afterEachTest() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, TABLE_SELLERS);
    }

    @Test
    @Sql("/sql/import-sellers.sql")
    public void testDeleteAllByJob() {
        sellerDAO.deleteAllByJobId("1");

        final int countRowsInSellers = JdbcTestUtils.countRowsInTable(jdbcTemplate, TABLE_SELLERS);

        assertThat(countRowsInSellers).isZero();
    }

    @Test
    public void testDeleteAllByJobWithoutData() {
        sellerDAO.deleteAllByJobId("1");

        final int countRowsInSellers = JdbcTestUtils.countRowsInTable(jdbcTemplate, TABLE_SELLERS);

        assertThat(countRowsInSellers).isZero();
    }

    @Test
    public void testSaveAll() {
        final Seller seller = new Seller();
        seller.setJobId(1L);
        seller.setDocument("123445645342");
        seller.setName("Andre");
        seller.setSalary(1500F);

        final ArrayList<Seller> sellers = new ArrayList<>();
        final int countSellers = 20;

        for (int i = 0; i < countSellers; i++) {
            sellers.add(seller);
        }

        sellerDAO.saveAll(sellers);

        final int countRowsInSellers = JdbcTestUtils.countRowsInTable(jdbcTemplate, TABLE_SELLERS);

        assertThat(countRowsInSellers).isEqualTo(countSellers);
    }

    @Test
    public void testSaveAllWithNullValues() {
        final Seller seller = new Seller();
        seller.setJobId(null);
        seller.setDocument(null);
        seller.setName(null);
        seller.setSalary(1500F);

        final ArrayList<Seller> sellers = new ArrayList<>();
        sellers.add(seller);

        Assertions.assertThrows(NullPointerException.class, () -> {
            sellerDAO.saveAll(sellers);
        });
    }

    @Test
    public void testSaveAllPassingNull() {
        sellerDAO.saveAll(null);

        final int countRowsInSellers = JdbcTestUtils.countRowsInTable(jdbcTemplate, TABLE_SELLERS);

        assertThat(countRowsInSellers).isZero();
    }
}
