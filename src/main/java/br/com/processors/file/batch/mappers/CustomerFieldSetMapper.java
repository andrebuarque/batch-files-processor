package br.com.processors.file.batch.mappers;

import br.com.processors.file.models.Customer;
import br.com.processors.file.models.FileItem;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class CustomerFieldSetMapper implements FieldSetMapper<FileItem> {
    @Value("#{jobParameters['job.id']}")
    private String jobId;

    @Override
    public Customer mapFieldSet(final FieldSet fieldSet) {
        final String document = fieldSet.readString("document");
        final String name = fieldSet.readString("name");
        final String businessArea = fieldSet.readString("businessArea");

        final Customer customer = new Customer();
        customer.setJobId(Long.parseLong(jobId));
        customer.setDocument(document);
        customer.setName(name);
        customer.setBusinessArea(businessArea);

        return customer;
    }
}
