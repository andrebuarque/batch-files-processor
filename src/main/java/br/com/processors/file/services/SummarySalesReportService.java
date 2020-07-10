package br.com.processors.file.services;

import br.com.processors.file.models.SummarySales;

public interface SummarySalesReportService {
    SummarySales execute(String jobId);
}
