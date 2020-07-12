package br.com.processors.file.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
@ActiveProfiles("test")
public class SaleRepositoryTest {
    @Autowired
    private SaleRepository saleRepository;

    @AfterEach
    public void afterEachTest() {
        saleRepository.deleteAll();
    }

    @Test
    @Sql("/sql/import-sales.sql")
    public void testDeleteAllByJob() {
        saleRepository.deleteByJobId(1L);

        final long countRowsInSales = saleRepository.count();

        assertThat(countRowsInSales).isZero();
    }

    @Test
    public void testDeleteAllByJobWithoutData() {
        saleRepository.deleteByJobId(1L);

        final long countRowsInSales = saleRepository.count();

        assertThat(countRowsInSales).isZero();
    }

    @Test
    public void testSaveAllPassingNull() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> saleRepository.saveAll(null));
    }
}
