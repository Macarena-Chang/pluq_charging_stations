package com.example.reporting.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Report {
    private String name;
    private int chargingSocketsCount;
    private double energyPrice;
    private int energyConsumption;
    private double totalCost;
    private double latitude;
    private double longitude;
}