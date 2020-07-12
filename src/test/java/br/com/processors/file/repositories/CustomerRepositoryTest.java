package br.com.processors.file.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;

    @AfterEach
    public void afterEachTest() {
        customerRepository.deleteAll();
    }

    @Test
    @Sql("/sql/import-customers.sql")
    public void testDeleteAllByJob() {
        customerRepository.deleteByJobId(1L);

        final long rowsInTable = customerRepository.count();

        assertThat(rowsInTable).isZero();
    }

    @Test
    public void testDeleteAllByJobWithoutData() {
        customerRepository.deleteByJobId(1L);

        final long rowsInTable = customerRepository.count();

        assertThat(rowsInTable).isZero();
    }

    @Test
    @Sql("/sql/import-customers.sql")
    public void testCountByJobId() {
        final Long countByJobId = customerRepository.countByJobId(1L);

        assertThat(countByJobId).isEqualTo(4L);
    }

    @Test
    public void testCountByJobIdWithoutData() {
        final Long countByJobId = customerRepository.countByJobId(1L);

        assertThat(countByJobId).isZero();
    }

    @Test
    public void testSaveAllPassingNull() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> customerRepository.saveAll(null));
    }
}
