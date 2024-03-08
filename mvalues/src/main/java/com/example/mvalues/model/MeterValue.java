package com.example.mvalues.model;

 import lombok.*;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Document(collection = "metervalues")
public class MeterValue {
    @Id
    private String date;
    private String dateAdded;
    private String physicalReference;
    private String transactionId;
    private Double meterValue;


}
