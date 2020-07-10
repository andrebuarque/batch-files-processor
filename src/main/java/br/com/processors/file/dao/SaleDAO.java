package br.com.processors.file.dao;

import br.com.processors.file.models.Sale;
import br.com.processors.file.models.SaleItem;
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
public class SaleDAO {
    private static final String QUERY_INSERT_SALE = "INSERT INTO sales (job_id, id, seller) VALUES (?, ?, ?)";
    private static final String QUERY_INSERT_SALE_ITEM = "INSERT INTO sale_items (job_id, sale_id, id, quantity, price) VALUES (?, ?, ?, ?, ?)";
    public static final String QUERY_BIGGEST_SALE_ID =
        """
        SELECT id FROM (
           SELECT sale.id, SUM(item.quantity * item.price) AS total
           FROM sales AS sale, sale_items item
           WHERE sale.id = item.sale_id
             AND sale.job_id = ?
             AND item.job_id = ?
           GROUP BY sale.id
           ORDER BY total DESC
           LIMIT 1)""";
    public static final String QUERY_WORST_SELLER =
               """
               SELECT seller FROM (
                   SELECT sale.seller, SUM(item.quantity * item.price) AS total
                   FROM sales AS sale, sale_items item
                   WHERE sale.id = item.sale_id
                     AND sale.job_id = ?
                     AND item.job_id = ?
                   GROUP BY sale.seller
                   ORDER BY total ASC
                   LIMIT 1
               )""";
    public static final String QUERY_DELETE_BY_JOB_ID =
        """
            DELETE FROM sales WHERE job_id = ?;
            DELETE FROM sale_items WHERE job_id = ?;
        """;

    private final NamedParameterJdbcTemplate parameterJdbcTemplate;

    public SaleDAO(final NamedParameterJdbcTemplate parameterJdbcTemplate) {
        this.parameterJdbcTemplate = parameterJdbcTemplate;
    }

    public void deleteAllByJobId(final String jobId) {
        final JdbcTemplate jdbcTemplate = parameterJdbcTemplate.getJdbcTemplate();

        jdbcTemplate.update(QUERY_DELETE_BY_JOB_ID, jobId, jobId);
    }

    public Long getBiggestSaleIdByJobId(final String jobId) {
        final JdbcTemplate jdbcTemplate = parameterJdbcTemplate.getJdbcTemplate();

        try {
            return jdbcTemplate.queryForObject(QUERY_BIGGEST_SALE_ID, new Object[] { jobId, jobId }, Long.class);
        } catch (final EmptyResultDataAccessException ex) {
            return 0L;
        }
    }

    public String getWorstSellerByJobId(final String jobId) {
        final JdbcTemplate jdbcTemplate = parameterJdbcTemplate.getJdbcTemplate();

        try {
            return jdbcTemplate.queryForObject(QUERY_WORST_SELLER, new Object[] { jobId, jobId }, String.class);
        } catch (final EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public void saveAll(final List<Sale> sales) {
        if (CollectionUtils.isEmpty(sales)) {
            return;
        }

        parameterJdbcTemplate.getJdbcOperations().batchUpdate(QUERY_INSERT_SALE, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(final PreparedStatement preparedStatement, final int i) throws SQLException {
                final Sale sale = sales.get(i);

                preparedStatement.setLong(1, sale.getJobId());
                preparedStatement.setLong(2, sale.getId());
                preparedStatement.setString(3, sale.getSeller());
            }

            @Override
            public int getBatchSize() {
                return sales.size();
            }
        });

        sales.forEach(sale -> {
            final Long saleId = sale.getId();
            final List<SaleItem> items = sale.getItems();

            parameterJdbcTemplate.getJdbcOperations().batchUpdate(QUERY_INSERT_SALE_ITEM, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(final PreparedStatement preparedStatement, final int i) throws SQLException {
                    final SaleItem item = items.get(i);

                    preparedStatement.setLong(1, sale.getJobId());
                    preparedStatement.setLong(2, saleId);
                    preparedStatement.setLong(3, item.id());
                    preparedStatement.setInt(4, item.quantity());
                    preparedStatement.setFloat(5, item.price());
                }

                @Override
                public int getBatchSize() {
                    return items.size();
                }
            });
        });
    }
}
