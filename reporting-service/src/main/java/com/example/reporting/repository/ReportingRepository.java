package com.example.reporting.repository;
import com.example.reporting.dto.ReportDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface ReportingRepository extends MongoRepository<ReportDTO, String>{
}
