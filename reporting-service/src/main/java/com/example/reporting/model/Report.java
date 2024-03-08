package com.example.reporting.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Report {
  // needs to be modified to include all the data that is needed
  private String city;
  private int chargingSockets;
  private double totalKwhCharged;
  private int chargingSessions;
  private double kwhChargedPerSocket;
  private double kwhChargedPerSession;
  private double kwhChargedPerDayPerSocket;

  public Report() {

  }
}