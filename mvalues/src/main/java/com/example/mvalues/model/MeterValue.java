package com.example.mvalues.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;


@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Document(collection = "metervalues")
public class MeterValue {
    @MongoId
    private ObjectId _id;
    private String date;
    private String dateAdded;
    private String physicalReference;
    private String transactionId;
    private Double meterValue;


}
