package br.com.processors.file.models;

import javax.validation.constraints.NotEmpty;

public class Person {
    protected String document;
    @NotEmpty
    protected String name;

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
