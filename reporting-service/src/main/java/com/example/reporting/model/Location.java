package com.example.reporting.model;

import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor  
@AllArgsConstructor
@Data
public class Location {

    private String id;
    private String name;
    private String type;
    private String address;
    private String city;
    private String postal_Code;
    private String country;
    private Coordinates coordinates;
    private boolean chargingWhenClosed;
    private int chargeSockets;
    private String lastUpdated;
    private List<EVSE> evses;
}

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

