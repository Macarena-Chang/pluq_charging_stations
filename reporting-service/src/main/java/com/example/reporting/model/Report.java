package com.example.reporting.model;

import java.util.TreeMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@Data
@ToString
  public class Report {
    private String locationName;
    private int amountOfChargingSockets;
    private double totalAmountOfKwhCharged;
    private int amountOfChargingSessions;
    private double amountOfKwhChargedPerSocket;
    private double amountOfKwhChargedPerSession;
   private TreeMap<String, TreeMap<String, Double>> amountOfKwhChargedPerDayPerSocket;

  public Report() {

  }
}