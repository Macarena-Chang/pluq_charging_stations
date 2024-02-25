package com.example.locationsservice.entities;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Data
@Document(collection = "locations")
public class Location {
    @MongoId
    private ObjectId _id;
    private String id;
    private String name;
    private String type;
    private String address;
    private String city;
    private String postalCode;
    private String country;
    private Coordinates coordinates;
    private boolean chargingWhenClosed;
    private int chargeSockets;
    private String lastUpdated;
    private List<EVSE> evses;}

@Data
class Coordinates {
    private String latitude;
    private String longitude;

}
@Data
class EVSE {
    private String uid;
    private String evseId;
    private String status;
    private String lastUpdated;
    private List<String> capabilities;
    private String physicalReference;
    private List<Connector> connectors;

}
@Data
class Connector {
    private String id;
    private String standard;
    private String format;
    private String powerType;
    private int voltage;
    private int amperage;
    private String lastUpdated;
    private String tariffId;

}

