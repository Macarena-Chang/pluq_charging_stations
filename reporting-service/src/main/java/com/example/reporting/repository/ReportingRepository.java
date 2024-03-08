package com.example.reporting.repository;
import com.example.reporting.model.Report;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface ReportingRepository extends MongoRepository<Report, String>{
}
