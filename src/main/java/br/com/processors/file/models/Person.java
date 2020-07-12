package br.com.processors.file.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Person implements FileItem {
    @Id
    @GeneratedValue
    private Long id;

    @Positive
    @NotNull
    private Long jobId;

    @NotEmpty
    protected String name;

    protected String document;

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

    public String getDocument() {
        return document;
    }

    public void setDocument(final String document) {
        this.document = document;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
