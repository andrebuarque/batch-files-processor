package br.com.processors.file.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.List;

public class Sale implements FileItem {

    @Positive
    private Long jobId;
    @Positive
    private Long id;
    @NotEmpty
    private String seller;
    @NotEmpty
    private List<SaleItem> items;

    @Override
    public Long getJobId() {
        return jobId;
    }

    @Override
    public void setJobId(final Long jobId) {
        this.jobId = jobId;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Sale{" +
            "jobId='" + jobId + '\'' +
            ", id=" + id +
            ", seller='" + seller + '\'' +
            ", items=" + items +
            '}';
    }
}
