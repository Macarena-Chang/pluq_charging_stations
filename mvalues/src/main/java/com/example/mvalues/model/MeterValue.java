package com.example.mvalues.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Document(collection = "metervalues")
public class MeterValue {
    @Id
    private String id;
    private Instant date;
    private Instant dateAdded;
    private String physicalReference;
    private String transactionId;
    private double meterValue;


}
