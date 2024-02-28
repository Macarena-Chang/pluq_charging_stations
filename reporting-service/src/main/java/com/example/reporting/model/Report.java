package com.example.reporting.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder

@RequiredArgsConstructor
@Document(collection = "reports")
public class Report {

}