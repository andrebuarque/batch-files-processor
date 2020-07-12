package br.com.processors.file.repositories;

import br.com.processors.file.models.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
    Long countByJobId(Long jobId);
    void deleteByJobId(Long jobId);
}
