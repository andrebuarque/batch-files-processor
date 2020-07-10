package br.com.processors.file.batch.mappers;

import br.com.processors.file.models.FileItem;
import br.com.processors.file.models.Seller;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class SellerFieldSetMapper implements FieldSetMapper<FileItem> {
    @Value("#{jobParameters['job.id']}")
    private String jobId;

    @Override
    public FileItem mapFieldSet(final FieldSet fieldSet) {
        final String document = fieldSet.readString("document");
        final String name = fieldSet.readString("name");
        final String salary = fieldSet.readString("salary");

        final Seller seller = new Seller();
        seller.setJobId(Long.parseLong(jobId));
        seller.setDocument(document);
        seller.setName(name);

        if (NumberUtils.isParsable(salary)) {
            seller.setSalary(Float.parseFloat(salary));
        }

        return seller;
    }
}
