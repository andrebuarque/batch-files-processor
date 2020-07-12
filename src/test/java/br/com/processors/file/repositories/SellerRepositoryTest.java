package br.com.processors.file.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@ActiveProfiles("test")
public class SellerRepositoryTest {
    @Autowired
    private SellerRepository sellerRepository;

    @AfterEach
    public void afterEachTest() {
        sellerRepository.deleteAll();
    }

    @Test
    @Sql("/sql/import-sellers.sql")
    public void testDeleteAllByJob() {
        sellerRepository.deleteByJobId(1L);

        final Long countRowsInSellers = sellerRepository.countByJobId(1L);

        assertThat(countRowsInSellers).isZero();
    }

    @Test
    public void testDeleteAllByJobWithoutData() {
        sellerRepository.deleteByJobId(1L);

        final Long countRowsInSellers = sellerRepository.countByJobId(1L);

        assertThat(countRowsInSellers).isZero();
    }

    @Test
    @Sql("/sql/import-sellers.sql")
    public void testCountByJobId() {
        final Long countByJobId = sellerRepository.countByJobId(1L);

        assertThat(countByJobId).isEqualTo(5L);
    }

    @Test
    public void testCountByJobIdWithoutData() {
        final Long countByJobId = sellerRepository.countByJobId(1L);

        assertThat(countByJobId).isZero();
    }

    @Test
    public void testSaveAllPassingNull() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> sellerRepository.saveAll(null));
    }
}