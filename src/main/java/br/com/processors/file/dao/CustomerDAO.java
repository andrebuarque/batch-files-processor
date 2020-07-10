package br.com.processors.file.dao;

import br.com.processors.file.models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CustomerDAO {
    private static final String QUERY_INSERT_SELLERS = "INSERT INTO customers (job_id, document, name, business_area) VALUES (?, ?, ?, ?)";
    public static final String QUERY_COUNT_BY_JOB_ID = "SELECT COUNT(*) FROM customers WHERE job_id = ?";
    public static final String QUERY_DELETE_BY_JOB_ID = "DELETE FROM customers WHERE job_id = ?";

    @Autowired
    private NamedParameterJdbcTemplate parameterJdbcTemplate;

    public void deleteAllByJobId(final String jobId) {
        final JdbcTemplate jdbcTemplate = parameterJdbcTemplate.getJdbcTemplate();

        jdbcTemplate.update(QUERY_DELETE_BY_JOB_ID, jobId);
    }

    public Long getCountByJobId(final String jobId) {
        final JdbcTemplate jdbcTemplate = parameterJdbcTemplate.getJdbcTemplate();

        try {
            return jdbcTemplate.queryForObject(QUERY_COUNT_BY_JOB_ID, new Object[] { jobId }, Long.class);
        } catch (final EmptyResultDataAccessException ex) {
            return 0L;
        }
    }

    public void saveAll(final List<Customer> customers) {
        if (CollectionUtils.isEmpty(customers)) {
            return;
        }

        parameterJdbcTemplate.getJdbcOperations().batchUpdate(QUERY_INSERT_SELLERS, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(final PreparedStatement preparedStatement, final int i) throws SQLException {
                final Customer customer = customers.get(i);

                preparedStatement.setLong(1, customer.getJobId());
                preparedStatement.setString(2, customer.getDocument());
                preparedStatement.setString(3, customer.getName());
                preparedStatement.setString(4, customer.getBusinessArea());
            }

            @Override
            public int getBatchSize() {
                return customers.size();
            }
        });
    }
}
