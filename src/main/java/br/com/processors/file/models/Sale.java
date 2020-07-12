package br.com.processors.file.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Objects;

@Entity
public class Sale implements FileItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Positive
    private Long jobId;

    @Positive
    private Long saleId;

    @NotEmpty
    private String seller;

    @NotEmpty
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "sale_id")
    private List<SaleItem> items;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Override
    public Long getJobId() {
        return jobId;
    }

    @Override
    public void setJobId(final Long jobId) {
        this.jobId = jobId;
    }

    public Long getSaleId() {
        return saleId;
    }

    public void setSaleId(final Long saleId) {
        this.saleId = saleId;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(final String seller) {
        this.seller = seller;
    }

    public List<SaleItem> getItems() {
        return items;
    }

    public void setItems(final List<SaleItem> items) {
        this.items = items;
    }

    public Double getTotal() {
        final Double initialValue = 0d;

        if (Objects.nonNull(this.items)) {
            return this.items.stream()
                .map(SaleItem::getTotal)
                .reduce(initialValue, Double::sum);
        }

        return initialValue;
    }
}
