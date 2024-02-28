package com.example.reporting.services;
import com.example.reporting.repository.ReportingRepository;
import org.springframework.stereotype.Service;

@Service
public class ReportingService {
private final ReportingRepository reportingrepo;
    public ReportingService(ReportingRepository reportingrepo) {
        this.reportingrepo = reportingrepo;
    }
}
