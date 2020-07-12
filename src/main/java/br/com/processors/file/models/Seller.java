package br.com.processors.file.models;

import javax.persistence.Entity;

@Entity
public class Seller extends Person {
    private Float salary;

    public Float getSalary() {
        return salary;
    }

    public void setSalary(final Float salary) {
        this.salary = salary;
    }
}
