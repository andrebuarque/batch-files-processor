package br.com.processors.file.repositories;

import br.com.processors.file.models.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    void deleteByJobId(Long jobId);
    List<Sale> findByJobId(Long jobId);
}
