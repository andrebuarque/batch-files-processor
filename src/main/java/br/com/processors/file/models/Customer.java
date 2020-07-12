package br.com.processors.file.models;

import javax.persistence.Entity;

@Entity
public class Customer extends Person {
    private String businessArea;

    public String getBusinessArea() {
        return businessArea;
    }

    public void setBusinessArea(final String businessArea) {
        this.businessArea = businessArea;
    }
}
