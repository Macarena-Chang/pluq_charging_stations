package com.example.reporting.dto;

import com.example.reporting.model.MeterValue;
import com.example.reporting.model.SessionInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class LocationDTO {

  private String city;
  private int chargingSockets;
  private ArrayList<String> uids;
  @JsonIgnore
  private ArrayList<MeterValue> meterValuesList;
  private double totalKwhCharged;
  private int sessions; //amount of sessions for the city
  private ArrayList<String> sessionsList;
 private Map<String, SessionInfo> chargedKwhPerSession; //charged kwh per session
  private Map<String, Double> chargedPerSocket;
  TreeMap<String, TreeMap<String, Double>>  chargedPerSocketPerDay;

  public LocationDTO() {
  }
}
