package br.com.processors.file.models;

import javax.validation.constraints.Positive;

public class Customer extends Person implements FileItem {
    @Positive
    private Long jobId;
    private String businessArea;

    @Override
    public Long getJobId() {
        return jobId;
    }

    @Override
    public void setJobId(final Long jobId) {
        this.jobId = jobId;
    }

    public String getBusinessArea() {
        return businessArea;
    }

    public void setBusinessArea(final String businessArea) {
        this.businessArea = businessArea;
    }

    @Override
    public String toString() {
        return "Customer{" +
            "jobId='" + jobId + '\'' +
            ", businessArea='" + businessArea + '\'' +
            ", document='" + document + '\'' +
            ", name='" + name + '\'' +
            '}';
    }
}
