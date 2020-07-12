package br.com.processors.file.models;

import javax.persistence.*;

@Entity
public class SaleItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long itemId;
    private Integer quantity;
    private Float price;

    @ManyToOne
    private Sale sale;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(final Long itemId) {
        this.itemId = itemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(final Integer quantity) {
        this.quantity = quantity;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(final Float price) {
        this.price = price;
    }

    public Double getTotal() {
        return (double) quantity * price;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(final Sale sale) {
        this.sale = sale;
    }
}
