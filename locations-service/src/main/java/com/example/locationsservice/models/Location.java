package com.example.locationsservice.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "locations")
public class Location {
    //TODO add fields.
}
