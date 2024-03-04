package com.example.reporting.dto;

import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LocationDTO
{
  private String city;
  private int chargingSockets;
  private ArrayList<String> uids;

    public LocationDTO() {
  }
}

