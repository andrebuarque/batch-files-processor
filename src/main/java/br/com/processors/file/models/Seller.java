package br.com.processors.file.models;

import javax.validation.constraints.Positive;

public class Seller extends Person implements FileItem {
    @Positive
    private Long jobId;
    private Float salary;

    @Override
    public Long getJobId() {
        return jobId;
    }

    @Override
    public void setJobId(final Long jobId) {
        this.jobId = jobId;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(final Float salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Seller{" +
            "jobId='" + jobId + '\'' +
            ", salary=" + salary +
            ", document='" + document + '\'' +
            ", name='" + name + '\'' +
            '}';
    }
}
