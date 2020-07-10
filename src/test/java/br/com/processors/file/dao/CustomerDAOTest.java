package br.com.processors.file.dao;

import br.com.processors.file.models.Customer;
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

import static br.com.processors.file.TestConstants.TABLE_CUSTOMERS;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class CustomerDAOTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CustomerDAO customerDAO;

    @AfterEach
    public void afterEachTest() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, TABLE_CUSTOMERS);
    }

    @Test
    @Sql("/sql/import-customers.sql")
    public void testDeleteAllByJob() {
        customerDAO.deleteAllByJobId("1");

        final int rowsInTable = JdbcTestUtils.countRowsInTable(jdbcTemplate, TABLE_CUSTOMERS);

        assertThat(rowsInTable).isZero();
    }

    @Test
    public void testDeleteAllByJobWithoutData() {
        customerDAO.deleteAllByJobId("1");

        final int rowsInTable = JdbcTestUtils.countRowsInTable(jdbcTemplate, TABLE_CUSTOMERS);

        assertThat(rowsInTable).isZero();
    }

    @Test
    @Sql("/sql/import-customers.sql")
    public void testCountByJobId() {
        final Long countByJobId = customerDAO.getCountByJobId("1");

        assertThat(countByJobId).isEqualTo(4L);
    }

    @Test
    public void testCountByJobIdWithoutData() {
        final Long countByJobId = customerDAO.getCountByJobId("1");

        assertThat(countByJobId).isZero();
    }

    @Test
    public void testSaveAll() {
        final Customer customer = new Customer();
        customer.setJobId(1L);
        customer.setName("Andre");
        customer.setName("1234567891234");
        customer.setBusinessArea("Business Area");

        final ArrayList<Customer> customers = new ArrayList<>();
        final int countCustomers = 20;
        for (int i = 0; i < countCustomers; i++) {
            customers.add(customer);
        }

        customerDAO.saveAll(customers);

        final int countRowsInTable = JdbcTestUtils.countRowsInTable(jdbcTemplate, TABLE_CUSTOMERS);
        assertThat(countRowsInTable).isEqualTo(countCustomers);
    }

    @Test
    public void testSaveAllWithNullValues() {
        final Customer customer = new Customer();
        customer.setJobId(null);
        customer.setName(null);
        customer.setName("1234567891234");
        customer.setBusinessArea("Business Area");

        final ArrayList<Customer> customers = new ArrayList<>();
        customers.add(customer);

        Assertions.assertThrows(NullPointerException.class, () -> {
            customerDAO.saveAll(customers);
        });
    }

    @Test
    public void testSaveAllPassingNull() {
        customerDAO.saveAll(null);

        final int countRowsInTable = JdbcTestUtils.countRowsInTable(jdbcTemplate, TABLE_CUSTOMERS);

        assertThat(countRowsInTable).isZero();
    }
}
