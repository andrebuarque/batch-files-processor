package br.com.processors.file.repositories;

import br.com.processors.file.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Long countByJobId(Long jobId);
    void deleteByJobId(Long jobId);
}
